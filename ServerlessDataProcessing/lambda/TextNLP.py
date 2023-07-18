import json
import spacy
import boto3
from collections import defaultdict

def lambda_handler(event, context):

    nlp = spacy.load('/opt/en_core_web_sm-2.1.0')

    # Get the S3 bucket name and object key from the event
    bucket_name = event['Records'][0]['s3']['bucket']['name']
    object_key = event['Records'][0]['s3']['object']['key']

    print('Bucket Name: {} - Object Name: {}'.format(bucket_name, object_key))

    # Create an S3 client
    s3_client = boto3.client('s3')

    # Read the content of the object
    response = s3_client.get_object(Bucket=bucket_name, Key=object_key)
    content = response['Body'].read().decode('utf-8')

    word_frequencies = {}
    my_set = {'apple'}  # Existing set

    nlp_test = nlp(content)

    doc = nlp(content)

    # for entity in doc.ents:
        # print(entity.text, entity.label_)


    unwanted_entity_types = ["CARDINAL", "PERCENT", "QUANTITY", "TIME", "DATE", "ORDINAL", "MONEY"]
    for entity in nlp_test.ents:
        if entity.label_ in unwanted_entity_types:
            my_set.add(str(entity))
            continue
        if entity.text[0].isupper() or entity.text.isupper():
            if str(entity) in word_frequencies:
                word_frequencies[str(entity)] += 1
            else:
                word_frequencies[str(entity)] = 1

    print(word_frequencies)
    print(my_set)

    newFileName = object_key.split(".")[0] + 'ne.txt'


    # Prepare data in the required format
    data = {newFileName: word_frequencies}


    file_path = f"/tmp/{newFileName}"  # Use /tmp directory for writable storage

    with open(file_path, "w") as f:
        json.dump(data, f)

    new_bucket_name = 'tagsb00913674'

    try:
        response = s3_client.upload_file(file_path, new_bucket_name, newFileName)
        return {
            'statusCode': 200
        }
    except ClientError as e:
        logging.error(e)
