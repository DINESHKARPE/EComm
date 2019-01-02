import json
import pandas as pd
from pandas.io.json import json_normalize

with open('a.json') as f:
    d = json.load(f)
data1 = json_normalize(d)
data2 = json_normalize(d['categories'])
data3 = json_normalize(d['rankings'])
data4 = json_normalize(d['rankings'],record_path='products',meta=['ranking'])
data5 = json_normalize(d['categories'],record_path=['products'])
data6 = json_normalize(d['categories'],record_path=['products','variants'])
data7 = json_normalize(d['categories'],record_path=['child_categories'],meta=['id','name','products'],errors='ignore')