from flask import Flask
from flask import request
import simplejson as json
import psycopg2


#------These are macros for column names------- 

listing_table_name = "Listing"
listing_tag_table_name = "Listing Tags"
listing_id_col_in_tag_table = "ListingID"
listing_cook_id_col = "CookID"
listing_food_name_col = "Food Name"
listing_pri_col = "Price"
listing_loc_col = "Location"
listing_img_col = "Image"
listing_id_col_in_listing_table = "ListingID"

def encase_in_quotes(stringy):
	return "\"" + stringy + "\""


#---------------------------------------------- 
	

app = Flask(__name__)

conn = psycopg2.connect(host="mydbinstance.cqzm55sjgiup.us-east-1.rds.amazonaws.com", database="csc301breadwiener",
user="csc301breadwiener", password="team7ithink")

cur = conn.cursor()



@app.route('/')
def hello():
	return "hi"

@app.route('/api/add', methods=['GET', 'POST'])
def addReq():
	if request.method == "GET":
		return printTables()
	elif request.method == "POST":
		addToDB(request.get_json())
		conn.commit()
		return "Success"


"""
Adds the Listing entry to the PSQL database with the given JSONdata
JSON format is a dictionary where the keys are the column names of the listing, along with
a key "tagList" which is a list of tags:

"""
def addToDB(json_data):
	json_dict = json_data
	list_id = getListId()
	cook_id = json_dict[listing_cook_id_col]
	food_name = json_dict[listing_food_name_col]
	price = json_dict[listing_pri_col]
	loc = json_dict[listing_loc_col]
	image = json_dict[listing_img_col]
	tags = json_dict["tags"]
	inserted = (list_id, cook_id, food_name, price, loc, image)
	sql = "INSERT INTO {} VALUES {}".format(encase_in_quotes(listing_table_name), str(inserted) )
	cur.execute(sql)
	
	addTags(tags, list_id)


	
"""
Adds a list of tags tag_list for a given listing with listing_id to the database
"""	
def addTags(tag_list, listing_id):
	for x in tag_list:
		sql = "INSERT INTO {} VALUES {}".format(encase_in_quotes(listing_tag_table_name), str((listing_id, x)))
		cur.execute(sql)
		
	
	
""" Returns an unused listing_id """
def getListId():
	sql = "SELECT max({}) FROM {}".format(encase_in_quotes(listing_id_col_in_listing_table), encase_in_quotes(listing_table_name))
	cur.execute(sql)
	maxID = cur.fetchone()
	if maxID[0] == None:
		return 1
	else:
		return maxID[0] + 1
		
		
def printTables():
	strout = "--------------------------ListingTable---------------------------<br>"
	sql = "SELECT * FROM {}".format( encase_in_quotes(listing_table_name))
	cur.execute(sql)
	listings = cur.fetchall()
	for x in listings:
		for y in x:
			strout = strout + str(y) + "||	"
		strout = strout + "<br>"
	sql = "SELECT * FROM {}".format(encase_in_quotes(listing_tag_table_name))
	cur.execute(sql)
	listings = cur.fetchall()
	strout += "<br><br><br>--------------------------TagTable-------------------------<br>"
	for x in listings:
		for y in x:
			strout = strout + str(y) + "	"

		strout = strout  + "<br>"
	return strout
		

	