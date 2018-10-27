from flask import Flask, jsonify
app = Flask(__name__) # instantiate flask, pass in current module
import psycopg2


# Connect to database.
try:
    conn = psycopg2.connect(host="mydbinstance.cqzm55sjgiup.us-east-1.rds.amazonaws.com", 
            database="csc301breadwiener",  user = "csc301breadwiener", password="team7ithink") 
except Exception as e:
    raise Exception(e)


@app.route("/api/markComplete/<int:listing_id>", methods = ['GET'])
def mark_as_complete(listing_id):
    """ A function that changes the status of the order with listing id listing_id to complete.
        Returns "Done" on a sucessful change of the listing id's order to complete.
    
        @param listing_id: the listing id number to delete.
        @rtype: str

    """
    cur = conn.cursor()
    try:
        cur.execute(
        """
            UPDATE public.\"Order\" 
            SET \"Status\" = \'Completed\'
            WHERE \"ListingID\" = 2
        """, (str(listing_id)))

        conn.commit()
    except Exception as e:
        raise Exception(e)

    return "Done"

if __name__ == "__main__":

    app.run(debug=True)

