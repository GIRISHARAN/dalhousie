S3EventNotificationRecord record = s3event.getRecords().get(0);
	    String srcBucket = record.getS3().getBucket().getName();
	    String dstBucket = 	"destinationBucketName";	
	    String srcKey = record.getS3().getObject().getKey().replace('+', ' '); //srcKey is the file name to be read with absolute path 
                System.out.println("srcKey:"+srcKey);
                try {
AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();         
S3Object s3Object = s3Client.getObject(new GetObjectRequest(
                    srcBucket, srcKey));	//get object file using source bucket and srcKey name
           	       	   InputStream objectData = s3Object.getObjectContent(); //get content of the file
		   String textToUpload = "";
		   Scanner scanner = new Scanner(objectData);	//scanning data line by line
	               while (scanner.hasNext()) {
        		         textToUpload += scanner.nextLine();
	               }
	               scanner.close();
	        	   String objectKey = "output.txt";	// create object key ie. file to be uploaded
	               ObjectMetadata meta = null;InputStream is = null;
	               try {
        		          byte[] bytes = textToUpload.getBytes("UTF-8");
        		          is = new ByteArrayInputStream(bytes);
        		
		          //set meta information about text to be uploaded
        		          meta = new ObjectMetadata();	        
        		          meta.setContentLength(bytes.length);     
          meta.setContentType("text/html");
     
        	                } catch (IOException e) {
        		          e.printStackTrace();
        	                }
		   //finally upload the file by specifying destination bucket name, file name, input stream having content to be uploaded along with meta information
        	              s3Client.putObject(dstBucket, objectKey, is, meta);
    		  System.out.println("File uploaded.");
    		  return "Success";
       	    }
              catch(AmazonServiceException e) {
                  // The call was transmitted successfully, but Amazon S3 couldn't process 
                  // it, so it returned an error response.
                  e.printStackTrace();
                  return "error";
             }
             catch(SdkClientException e) {
             	// Amazon S3 couldn't be contacted for a response, or the client  
            	// couldn't parse the response from Amazon S3.
            	e.printStackTrace();
            	return "error";
            }