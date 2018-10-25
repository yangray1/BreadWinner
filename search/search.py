from flask import Flask, jsonify
import psycopg2

app = Flask(__name__)

conn = psycopg2.connect(host="mydbinstance.cqzm55sjgiup.us-east-1.rds.amazonaws.com", database="csc301breadwiener", user="csc301breadwiener", password="team7ithink")

@app.route('/')
def search():
    cur = conn.cursor()
    cur.execute("SELECT * FROM public.\"User\"")
    row = cur.fetchone()

    return jsonify({'row': row})

if __name__ == '__main__':
   app.run()