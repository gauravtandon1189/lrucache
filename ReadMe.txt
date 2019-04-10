This repository includes an LRUCacheService that maintains an LRUCache of size 2 and exposes a REST API
with the functions - GET and PUT that can be invoked as -

curl -X GET http://localhost:8080/lrucache/api/v1/get/1
(1 = key)

curl -X PUT http://localhost:8080/lrucache/api/v1/put/1/10
(1 = key and 10 = value)

The following test scenarios were run on command line and all resulted in successful result.

curl -X PUT http://localhost:8080/lrucache/api/v1/put/1/400
{
}

curl -X PUT http://localhost:8080/lrucache/api/v1/put/2/800
{
}

curl -X GET http://localhost:8080/lrucache/api/v1/get/1
{
  key: "1",
  value: 400
}

curl -X PUT http://localhost:8080/lrucache/api/v1/put/3/1200  //evicts key=2
{
  key: "2",
  value: 800
}

curl -X GET http://localhost:8080/lrucache/api/v1/get/2
404

curl -X PUT http://localhost:8080/lrucache/api/v1/put/4/1600  //evicts key=1
{
  key: "1",
  value: 400
}

curl -X GET http://localhost:8080/lrucache/api/v1/get/1
404

curl -X GET http://localhost:8080/lrucache/api/v1/get/3
{
  key: "3",
  value: 1200
}

curl -X GET http://localhost:8080/lrucache/api/v1/get/4
{
  key: "4",
  value: 1600
}

