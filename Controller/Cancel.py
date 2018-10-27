from flask import Flask
import psycopg2
import simplejson

app = Flask(__name__)

conn = psycopg2.connect(host="mydbinstance.cqzm55sjgiup.us-east-1.rds.amazonaws.com", database="csc301breadwiener",
                        user="csc301breadwiener", password="team7ithink")


@app.route('/api/cancel/<int:clientId>/<int:listingId>', methods=['DELETE'])
def cancel(clientId, listingId):
    """
    Return a string representation of a list of JSON objects. This list contains
    objects that correspond to listings that match names or tags in the search query.
    """

    in_progress = get_in_progress_order(clientId, listingId)

    if in_progress:
        cancel_order(clientId, listingId)

    order_to_json(in_progress)  # want to convert each row into a JSON string

    return ''.join(in_progress)  # convert to string before returning


def get_in_progress_order(clientId, listingId):
    """
    Return a list of listing tuples whose tags correspond to words in search_terms.
    """
    matched_rows = []

    order = conn.cursor()
    order.execute("SELECT t1.\"ClientID\", t1.\"ListingID\", t1.\"Status\", t1.\"Time of Order\" from public.\"Order\""
                  " as t1 WHERE t1.\"ClientID\" = " + str(clientId) + " AND \"ListingID\" = " + str(listingId) +
                  " AND t1.\"Status\" = \'In progress\'")

    order_row = order.fetchone()

    while order_row is not None:
        matched_rows.append(order_row)
        order_row = order.fetchone()

    order.close()

    return matched_rows


def cancel_order(clientId, listingId):
    """
    given a clientId and listingId cancel the order in progress associated with the 2 ids
    """
    order = conn.cursor()
    order.execute(
        "UPDATE public.\"Order\" SET \"Status\" = 'Canceled' WHERE \"ClientID\" = " + str(clientId) +
        " AND \"ListingID\" = " + str(listingId) + " AND \"Status\" = \'In progress\'")
    conn.commit()

    order.close()


def order_to_json(rows):
    """
    Mutate rows such that each tuple in rows is converted to a JSON string representing the same information.
    """
    for i in range(len(rows)):
        rows[i] = simplejson.dumps({'ClientID': rows[i][0],
                                    'ListingID': rows[i][1],
                                    'Status': rows[i][2]})


if __name__ == '__main__':
    app.run()
