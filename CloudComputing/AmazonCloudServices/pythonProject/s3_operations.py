import boto3

bucketName = "girisharan-s3bucket-cloudcomputing"
objectName = "index.txt"

accessKey = "ASIA5OB2M6VCSBNL3HHD"
secretKey = "ZkTWXXGsw+lnzvo5daI4RWEzFjrFzRGMMKxlNDRk"
sessionToken = "FwoGZXIvYXdzEHsaDFipqRa601cZO2+ITSLAAefi3dofYPwuBoy+M" \
               "+h8UH4sEZSx0MOfiihauaO3ezulvJXR0JzDWOOtXYnzJCpstfn69bGqNcd01" \
               "+VQrQ8MOIjbHFvLafAquY37QqnNZIZFYy7rsXCSg4IkzhrvwOlFpcEr4PHW4GUZfqhI8EFw4fGRgZC85qAIw7Z5HSRyLGi" \
               "+7JjgOUOC5EUgV2Cs8kExgvKv2rkOuDZVtMQmxSXPb4ngOR59nTzD5RPyip2lVXXKM1qsQ8n7bqVe69falPfc8Ciq1JSkBjItOhBcIMJBPYMKF+G59MBHzwsAemIYuxPm2REfDXXn/Q10BGwuTlRiFput4bDM"

selectedRegion = "us-east-1"

s3Client = s3 = boto3.client('s3', region_name=selectedRegion, aws_access_key_id=accessKey, aws_secret_access_key=secretKey, aws_session_token=sessionToken)


def storeDataToObject(requestData):

    print(requestData)

    response = s3Client.get_object(Bucket=bucketName, Key=objectName)

    existing_content = response["Body"].read().decode("utf-8")
    updated_content = existing_content + requestData

    # Upload the updated content as a new object
    updated_content_bytes = updated_content.encode("utf-8")
    s3Client.put_object(Body=updated_content_bytes, Bucket=bucketName, Key=objectName)

    object_url = f"https://{bucketName}.s3.{selectedRegion}.amazonaws.com/{objectName}"
    print("Object URL:", object_url)
    return object_url


def deleteObject():
    s3Client.delete_object(Bucket=bucketName, Key=objectName)
