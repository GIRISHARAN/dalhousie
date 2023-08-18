import functions_framework
from google.cloud import firestore
import json

@functions_framework.http
def hello_http(request):
    """HTTP Cloud Function.
    Args:
        request (flask.Request): The request object.
        <https://flask.palletsprojects.com/en/1.1.x/api/#incoming-request-data>
    Returns:
        The response text, or any set of values that can be turned into a
        Response object using `make_response`
        <https://flask.palletsprojects.com/en/1.1.x/api/#flask.make_response>.
    """
    request_json = request.get_json(silent=True)
    request_args = request.args

    # Parse the request JSON data
    request_data = request.get_json()
    if not request_data or 'quiz_id' not in request_data:
        return json.dumps({"error": "Invalid request. 'quiz_id' parameter is missing."}), 400

    # Get the 'quiz_id' parameter from the request
    quiz_id = request_data['quiz_id']

    # Initialize Firestore client
    db = firestore.Client()

    # Reference to the 'quiz' collection
    quiz_ref = db.collection('quiz')

    # Query to get the documents with the given 'quiz_id'
    query = quiz_ref.where('game_id', '==', quiz_id).get()

    # Initialize dictionaries to store the results
    teams_points = {}
    persons_points = {}

    # Process each document returned by the query
    for doc in query:
        doc_data = doc.to_dict()

        # Segregate based on 'team_id'
        team_id = doc_data.get('team_id')
        if team_id:
            if team_id not in teams_points:
                teams_points[team_id] = 0

            # Calculate total points of each team
            teams_points[team_id] += doc_data.get('points', 0)

            # Store points for each person (user) within each team
            user_id = doc_data.get('user_id')
            if user_id:
                if team_id not in persons_points:
                    persons_points[team_id] = {}
                persons_points[team_id][user_id] = doc_data.get('points', 0)

    # Create or update the 'teams_points' collection
    teams_points_collection_ref = db.collection('teams_points')
    teams_points_collection_ref.document(quiz_id).set(teams_points)

    # Create or update the 'persons_points' collection
    persons_points_collection_ref = db.collection('persons_points')
    persons_points_collection_ref.document(quiz_id).set(persons_points)

    result = {
        "quiz_id": quiz_id,
        "teams_points": teams_points,
        "persons_points": persons_points
    }

    return json.dumps(result), 200
