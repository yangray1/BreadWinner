from flask import Flask, jsonify
app = Flask(__name__) # instantiate flask, pass in current module
import psycopg2



#------These are macros for column names------- 

listing_table_name = "\"Order\"" 
listing_col_status = "\"Status\""
listing_col_listing_id = "\"ListingID\""
listing_col_client_id = "\"ClientID\""
completed ="\'Completed\'"

# Connect to database.
try:
    conn = psycopg2.connect(host="mydbinstance.cqzm55sjgiup.us-east-1.rds.amazonaws.com", 
            database="csc301breadwiener",  user = "csc301breadwiener", password="team7ithink") 
except Exception as e:
    raise Exception(e)


@app.route("/api/markComplete/<int:clientID>/<int:listingID>", methods = ['GET'])
def mark_as_complete(clientID, listingID):
    """ A function that changes the status of the order with listing id listing_id to complete.
        Returns "Success" on a sucessful change of the listing id's order to complete.
    
        @param listing_id: the listing id number to delete.
        @rtype: str
    """

    sql = \
    """
        UPDATE public.{}
        SET {} = {}
        WHERE {} = {} AND {} = {}
    """.format(listing_table_name, listing_col_status, completed, \
                    listing_col_listing_id, str(listingID), listing_col_client_id, str(clientID))


    cur = conn.cursor()
    try:
        cur.execute(sql)
        conn.commit()
    except Exception as e:
        raise Exception(e)

    return "Success"

if __name__ == "__main__":

    app.run(debug=True)


