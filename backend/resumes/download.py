import os
from google.oauth2 import service_account
from google.cloud import storage
os.sys.path.append(os.path.join(os.path.dirname(__file__), '..'))
from dev_settings import GOOGLE_APPLICATION_CREDENTIALS

GCLOUD_BUCKET_RESUMES = 'bc20-resumes'
BUCKET_MIN = 1600
BUCKET_MAX = 5000

# hacky authentication begins here, this was way too hard
with open('gcloud-key.json', 'w') as outfile:
    outfile.write(GOOGLE_APPLICATION_CREDENTIALS)
    outfile.close()
os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = str(os.path.join(os.path.dirname(__file__), '../../gcloud-key.json'))
client = storage.client.Client()
os.remove('gcloud-key.json') # important!!!

# set up some paths
bucket = client.get_bucket(GCLOUD_BUCKET_RESUMES)
files_dir = os.path.join(os.path.dirname(__file__), 'files')

# start downloading!
user_id = 1707
try:
    blob = bucket.get_blob(os.path.join(str(user_id), 'resume.pdf'))
    file_name = str(user_id)+'.pdf'
    with open(os.path.join(files_dir, file_name), 'wb+') as file_obj:
        blob.download_to_file(file_obj)
        file_obj.close()
except:
    print("Could not retrieve source file from bucket, user id", user_id)