from flask import Flask
app = Flask(__name__)
import psycopg2
import simplejson

#------These are macros for column names------- 
listing_table_name = "\"Listing\""
listing_listing_id_col = "\"ListingID\""
listing_cook_id_col = "\"CookID\""
listing_food_name_col = "\"Food Name\""
listing_price_col = "\"Price\""
listing_location_col = "\"Location\""

order_table_name = "\"Order\""
order_client_id_col = "\"ClientID\""
order_listing_id_col = "\"ListingID\""
order_status_col = "\"Status\""
completed ="\'Completed\'"

try:
    conn = psycopg2.connect(host="mydbinstance.cqzm55sjgiup.us-east-1.rds.amazonaws.com", 
                            database="csc301breadwiener",  user = "csc301breadwiener", password="team7ithink") 
except Exception as e:
    raise Exception(e)


@app.route("/api/checkHistory/<int:clientID>", methods = ['GET'])
def checkHistory(clientID):
    """ 
        Return a string representation of a list of JSON objects. This list contains
        objects that correspond to the order history of client id ClientID.

        @param clientID: the client id number.
        @rtype: str
    """

    cur = conn.cursor()
    query = \
        """
            SELECT t2.{}, t2.{}, t2.{}, t2.{}
            FROM public.{} as t1 FULL OUTER JOIN public.{} as t2 ON t1.{} = t2.{}
            WHERE t1.{} LIKE {} AND t1.{} = {}
        """.format(listing_food_name_col, listing_cook_id_col, listing_price_col, listing_location_col, 
                    order_table_name, listing_table_name, order_listing_id_col, listing_listing_id_col,
                    order_status_col, completed, order_client_id_col, str(clientID))
    try:
        cur.execute(query)
    except Exception as e:
        raise Exception(e)
    
    status = cur.fetchall()
    convert_to_json(status)
    return simplejson.dumps({'data': status})

def convert_to_json(rows):
    """
        Mutate rows such that each tuple in rows is converted to a JSON string representing the same information.
    """
   
    for i in range(len(rows)): 
        rows[i] = simplejson.dumps({'Food Name': rows[i][0],
                                    'CookID': rows[i][1],
                                    'Price': rows[i][2],
                                    'Location': rows[i][3]})


if __name__ == "__main__":

    app.run(debug=True)



