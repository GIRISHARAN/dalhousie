import boto3

def get_question_details(question_id, question_category):
    print('coming here')

    # Create a DynamoDB client
    dynamodb = boto3.client('dynamodb')

    # Define the get_item parameters
    get_item_params = {
        'TableName': 'QuizQuestions',
        'Key': {
            'uuidKey': {'S': question_id},
            'questionCategory': {'S': question_category}
        },
        'ProjectionExpression': 'uuidKey, questionCategory, questionLevel, correctAnswer, options, question'
    }

    # Retrieve the item from the DynamoDB table
    response = dynamodb.get_item(**get_item_params)

    item = response.get('Item', {})
    question_details = {
        'uuidKey': item.get('uuidKey', {}).get('S', None),
        'questionCategory': item.get('questionCategory', {}).get('S', None),
        'questionLevel': item.get('questionLevel', {}).get('S', None),
        'correctAnswer': item.get('correctAnswer', {}).get('S', None),
        'options': item.get('options', {}).get('SS', []),
        'question': item.get('question', {}).get('S', None)
    }
    print(question_details)
    return question_details

def lambda_handler(event, context):
    # Get the quizNumber from the request body
    quiz_number = event['quizNumber']
    
    # Create a DynamoDB client
    dynamodb = boto3.client('dynamodb')
    
    try:
        # Retrieve the item from the DynamoDB table
        response = dynamodb.get_item(
            TableName='Quizzes',
            Key={
                'quizNumber': {'S': quiz_number}
            },
            ProjectionExpression='quizNumber,quizCategory,quizQuestionNumbers'
        )
        
        print(response)
        
        # Check if the item exists in the DynamoDB response
        if 'Item' not in response:
            return {
                'statusCode': 404,
                'body': 'Quiz not found'
            }
        
        print('coming')
        # Extract the quizQuestionNumbers list values from the response
        quiz_question_numbers = response['Item']['quizQuestionNumbers']['SS']
        question_category = response['Item']['quizCategory']['S']
        
        print(question_category)
        
        # Retrieve question details for each question number
        questions = []
        for question_id in quiz_question_numbers:
            print(question_id)
            question_details = get_question_details(question_id, question_category)
            questions.append(question_details)
            print(question_details)
        
        # Create the response dictionary
        response_body = {
            'questions': questions
        }
        
        print(response_body)
        
        # Return the response
        return {
            'statusCode': 200,
            'body': response_body
        }
    except Exception as e:
        # Handle any errors that occur during the retrieval
        return {
            'statusCode': 500,
            'body': str(e)
        }
