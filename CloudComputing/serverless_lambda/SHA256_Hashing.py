import json
import hashlib
import requests

def lambda_handler(event, context):
    raw_value = event['input']['value']
    
    # Create a new SHA256 hash object
    sha256_hash = hashlib.sha256()

    # Encode the text as bytes and update the hash object
    sha256_hash.update(raw_value.encode('utf-8'))

    # Get the hexadecimal representation of the hash digest
    encrypted_text = sha256_hash.hexdigest()

    result = {
        "banner": "B00913674",
        "result": encrypted_text,
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
