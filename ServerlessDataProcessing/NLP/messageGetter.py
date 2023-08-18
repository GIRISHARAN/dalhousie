""" References:
[1]	Amazon.com. [Online]. Available:
    https://docs.aws.amazon.com/code-library/latest/ug/python_3_sns_code_examples.html.
    [Accessed: 22-Jul-2023].
[2]	“Sending and receiving messages in Amazon SQS - Boto3 1.28.9 documentation,” Amazonaws.com. [Online].
    Available: https://boto3.amazonaws.com/v1/documentation/api/latest/guide/sqs-example-sending-receiving-msgs.html.
    [Accessed: 22-Jul-2023].
"""

import json
import boto3

def lambda_handler(event, context):

    sns = boto3.client('sns')

    sns_topic_arn = 'arn:aws:sns:us-east-1:283491128229:messageTopic2'

    # Create an SQS client
    sqs = boto3.client('sqs')

    # Receive messages from the queue
    response = sqs.receive_message(
        QueueUrl='https://sqs.us-east-1.amazonaws.com/283491128229/firstQueue',
        MaxNumberOfMessages=1,  # Number of messages to retrieve at once
        WaitTimeSeconds=10  # Wait time for receiving messages
    )

    print(response)

    # Check if there are messages in the response
    if 'Messages' in response:
        message = response['Messages'][0]  # Get the first message

        # Extract the body of the message
        body = json.loads(message['Body'])

        # Getting the message sent by messageGenerator Code
        messageReceived = body['Message']

        print(messageReceived)

        snsResponse = sns.publish(
            TopicArn='arn:aws:sns:us-east-1:283491128229:messageTopic2',
            Message=messageReceived
            )

        print(snsResponse)

        # Delete the message from the queue after processing (optional, depending on your use case)
        receipt_handle = message['ReceiptHandle']
        sqs.delete_message(
            QueueUrl="https://sqs.us-east-1.amazonaws.com/283491128229/firstQueue",
            ReceiptHandle=receipt_handle
            )
        print('Message Deleted Successfully')

    return {
        'statusCode': 200,
        'body': json.dumps('SQS message processed successfully!')
    }