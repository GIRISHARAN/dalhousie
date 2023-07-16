import boto3

def lambda_handler(event, context):
    # Retrieve the UUID key and question level from the event
    uuid_key = event['uuid']
    questionCategory = event['questionCategory']

    # Create a Boto3 DynamoDB client
    dynamodb_client = boto3.client('dynamodb')

    # Specify the table name
    table_name = 'QuizQuestions'

    try:
        # Build the key values for the item to delete
        key_values = {
            'uuidKey': {'S': uuid_key},
            'questionCategory': {'S': questionCategory}
        }

        # Build the DeleteItem request to delete the item with the specified UUID key and question level
        delete_item_request = {
            'TableName': table_name,
            'Key': key_values
        }

        # Delete the item from the DynamoDB table
        response = dynamodb_client.delete_item(**delete_item_request)
        print(response)
        
        # Return a success response
        return {
            'statusCode': 200,
            'body': 'Item deleted successfully'
        }
    except Exception as e:
        print(e)
        
        # Return an error response
        return {
            'statusCode': 500,
            'body': 'Error occurred during item deletion'
        }
