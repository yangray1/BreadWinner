from flask import Flask
import simplejson as json
import psycopg2

app = Flask(__name__)

conn = psycopg2.connect(host="mydbinstance.cqzm55sjgiup.us-east-1.rds.amazonaws.com", database="csc301breadwiener",
user="csc301breadwiener", password="team7ithink")

@app.route('/api/add', methods=['GET', 'POST'])
def addReq():
	return


"""
Adds the Listing entry to the PSQL database with the given JSONdata
JSON format is a dictionary where the keys are the columns of the listing, along with
a key "tagList" which is a list of tags:

"""
def addToDB(json_data):
	json_dict = json_data
	return


	
"""
Adds a list of tags tag_list for a given listing with listing_id to the database
"""	
def addTags(tag_list, listing_id):
	return
