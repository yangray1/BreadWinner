from flask import Flask
import psycopg2
import simplejson

app = Flask(__name__)

conn = psycopg2.connect(host="mydbinstance.cqzm55sjgiup.us-east-1.rds.amazonaws.com", database="csc301breadwiener",
                        user="csc301breadwiener", password="team7ithink")


@app.route('/search/<string:search_query>', methods=['GET'])
def search(search_query):
    # separate words in search_query with '+' in place of spaces
    search_terms = search_query.split('+')
    rows_to_return = []

    for search_term in search_terms:
        search_names = conn.cursor()
        search_names.execute("SELECT t1.\"ListingID\", t1.\"CookID\", t1.\"Food Name\", t1.\"Price\","
                             " t1.\"Location\", t1.\"Image\" FROM public.\"Listing\" as t1"
                             " FULL OUTER JOIN public.\"Listing Tags\" as t2 ON t1.\"ListingID\" = t2.\"ListingID\" "
                             "WHERE UPPER(t1.\"Food Name\") LIKE UPPER(\'%{}%\')".format(search_term))

        search_names_row = search_names.fetchone()

        while search_names_row is not None:
            rows_to_return.append(search_names_row)
            search_names_row = search_names.fetchone()

        search_names.close()

        search_tags = conn.cursor()
        search_tags.execute("SELECT t1.\"ListingID\", t1.\"CookID\", t1.\"Food Name\", t1.\"Price\","
                             " t1.\"Location\", t1.\"Image\" FROM public.\"Listing\" as t1"
                             " FULL OUTER JOIN public.\"Listing Tags\" as t2 ON t1.\"ListingID\" = t2.\"ListingID\" "
                             "WHERE UPPER(t2.\"Tag\") LIKE UPPER(\'%{}%\')".format(search_term))

        search_tags_row = search_tags.fetchone()

        while search_tags_row is not None:
            rows_to_return.append(search_tags_row)
            search_tags_row = search_names.fetchone()

    print(rows_to_return, " before set")
    print(rows_to_return[0] == rows_to_return[1])
    print(type(rows_to_return[0]))
    print(type(rows_to_return[1]))
    list(set(rows_to_return))
    print(rows_to_return, " after set")


    return simplejson.dumps({'row': rows_to_return})


if __name__ == '__main__':
   app.run()