""" References:
[1]	Amazon.com. [Online]. Available:
    https://docs.aws.amazon.com/code-library/latest/ug/python_3_sns_code_examples.html.
    [Accessed: 22-Jul-2023]. """


import json
import random
import boto3

def lambda_handler(event, context):
    # TODO implement
    CAR = ["Compact", "mid-size Sedan", "SUV", "Luxury"]
    ADDON = ["GPS", "Camera"]
    ADDRESSES = ["6050 University Avenue", "1333 South Park St.", "5543 Clyde St."]

    selected_car = random.choice(CAR)
    selected_addon = random.choice(ADDON)
    selected_address = random.choice(ADDRESSES)

    response_message = f"You selected {selected_car} with {selected_addon} and location {selected_address}"

    # Replace 'YOUR_SNS_TOPIC_ARN' with the actual ARN of your SNS topic
    sns_topic_arn = 'arn:aws:sns:us-east-1:283491128229:messageTopic1'

    # Create an SNS client
    sns = boto3.client('sns')

    # Publish the message to the SNS topic
    snsResponse = sns.publish(
        TopicArn=sns_topic_arn,
        Message=response_message
    )

    print(snsResponse)

    return {
        'statusCode': 200,
        'body': response_message
    }