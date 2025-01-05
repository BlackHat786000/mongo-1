docker run -d --name mongo-replica -p 27017:27017 mongo:6.0 mongod --replSet rs0 --bind_ip_all


docker exec -it mongo-replica mongosh


rs.initiate({
  _id: "rs0",
  members: [{ _id: 0, host: "localhost:27017" }]
});


rs.status();
