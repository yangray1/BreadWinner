from flask import Flask
import psycopg2
import simplejson

app = Flask(__name__)

conn = psycopg2.connect(host="mydbinstance.cqzm55sjgiup.us-east-1.rds.amazonaws.com", database="csc301breadwiener",
                        user="csc301breadwiener", password="team7ithink")


@app.route('/api/search/<string:search_query>', methods=['GET'])
def search(search_query):
    """
    Return a string representation of a list of JSON objects. This list contains
    objects that correspond to listings that match names or tags in the search query.
    """
    # separate words in search_query with '+' in place of spaces
    search_terms = search_query.split('+')

    # want to remove whitespace and empty elements from the list
    search_terms_filtered = []

    for search_term in search_terms:
        if not search_term.isspace() and not search_term == '':
            search_terms_filtered.append(search_term)

    matched_rows_by_name = get_rows_from_name(search_terms_filtered)

    matched_rows_by_tag = get_rows_from_tag(search_terms_filtered)

    matched_rows = matched_rows_by_name + matched_rows_by_tag

    unique_matched_rows = list(set(matched_rows))  # remove duplicate rows

    rows_to_json(unique_matched_rows)  # want to convert each row into a JSON string

    return simplejson.dumps({'data': unique_matched_rows})  # convert to string before returning


def get_rows_from_name(search_terms):
    """
    Return a list of listing tuples whose Food Names correspond to words in search_terms.
    """
    matched_rows = []

    for search_term in search_terms:
        search_names = conn.cursor()
        search_names.execute("SELECT t1.\"ListingID\", t1.\"CookID\", t1.\"Food Name\", t1.\"Price\","
                             " t1.\"Location\", t1.\"Image\" FROM public.\"Listing\" as t1"
                             " FULL OUTER JOIN public.\"Listing Tags\" as t2 ON t1.\"ListingID\" = t2.\"ListingID\" "
                             "WHERE UPPER(t1.\"Food Name\") LIKE UPPER(\'%{}%\')".format(search_term))

        search_names_row = search_names.fetchone()

        while search_names_row is not None:
            matched_rows.append(search_names_row)
            search_names_row = search_names.fetchone()

        search_names.close()

    return matched_rows


def get_rows_from_tag(search_terms):
    """
    Return a list of listing tuples whose tags correspond to words in search_terms.
    """
    matched_rows = []

    for search_term in search_terms:
        search_tags = conn.cursor()
        search_tags.execute("SELECT t1.\"ListingID\", t1.\"CookID\", t1.\"Food Name\", t1.\"Price\","
                            " t1.\"Location\", t1.\"Image\" FROM public.\"Listing\" as t1"
                            " FULL OUTER JOIN public.\"Listing Tags\" as t2 ON t1.\"ListingID\" = t2.\"ListingID\" "
                            "WHERE UPPER(t2.\"Tag\") LIKE UPPER(\'%{}%\')".format(search_term))

        search_tags_row = search_tags.fetchone()

        while search_tags_row is not None:
            matched_rows.append(search_tags_row)
            search_tags_row = search_tags.fetchone()

        search_tags.close()

    return matched_rows


def rows_to_json(rows):
    """
    Mutate rows such that each tuple in rows is converted to a JSON string representing the same information.
    """
    for i in range(len(rows)):
        rows[i] = simplejson.dumps({'ListingID': rows[i][0],
                                    'CookID': rows[i][1],
                                    'Food Name': rows[i][2],
                                    'Price': rows[i][3],
                                    'Location': rows[i][4],
                                    'Image': rows[i][5]})


if __name__ == '__main__':
   app.run()