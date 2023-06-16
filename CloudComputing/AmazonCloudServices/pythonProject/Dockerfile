FROM ubuntu:latest

WORKDIR /usr/src/app

COPY . .

RUN apt-get update && apt-get upgrade -y && \
    apt-get install -y python3 python3-pip

RUN pip3 install --no-cache-dir -r requirements.txt

EXPOSE 50051

CMD ["python3", "./main.py"]