import json
import bcrypt
import requests

def lambda_handler(event, context):
    
    raw_value = event['input']['value']
    
    # converting password to an array of bytes
    bytes = raw_value.encode('utf-8')
    
    # generating the salt
    salt = bcrypt.gensalt()
    
    # Hashing the password
    encrypted_text = bcrypt.hashpw(bytes, salt)
    
    result = {
        "banner": "B00913674",
        "result": encrypted_text.decode('utf-8'),  # Convert the bytes to a string
        "arn": context.invoked_function_arn,
        "action": event['input']['action'],
        "value": raw_value
    }

    # Convert the 'result' dictionary to a JSON string
    result_json = json.dumps(result)

    # Define the URL to send the POST request
    url = "https://v7qaxwoyrb.execute-api.us-east-1.amazonaws.com/default/end"

    # Set the headers for the POST request
    headers = {
        "Content-Type": "application/json"
    }

    # Send the POST request with the JSON payload
    response = requests.post(url, headers=headers, data=result_json)

    # Check the response status code
    if response.status_code == 200:
        print("POST request successful.")
    else:
        print(f"POST request failed with status code: {response.status_code}")
        print(response.text)

    return result
