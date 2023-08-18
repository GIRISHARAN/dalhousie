import json
import boto3

def lambda_handler(event, context):
    # Get the S3 bucket name and object key from the event
    bucket_name = event['Records'][0]['s3']['bucket']['name']
    object_key = event['Records'][0]['s3']['object']['key']

    # Create an S3 client
    s3_client = boto3.client('s3')

    # Read the content of the object
    response = s3_client.get_object(Bucket=bucket_name, Key=object_key)
    content = response['Body'].read().decode('utf-8')

    # Parse the content and extract the required data
    content_data = json.loads(content)[object_key]

    # Create or get the DynamoDB resource
    dynamodb_resource = boto3.resource('dynamodb')
    table_name = 'serverless-assignment'
    table = dynamodb_resource.Table(table_name)

    # Update the DynamoDB table with the content data
    for key, value in content_data.items():
        response = table.update_item(
            Key={'entity-name': key},
            UpdateExpression='ADD #attrName :value',
            ExpressionAttributeNames={'#attrName': 'value'},
            ExpressionAttributeValues={':value': value},
            ReturnValues='UPDATED_NEW'
        )

    print("Data written to DynamoDB table:", table_name)
