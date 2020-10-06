const express = require("express")
const app = express()
const mongoClient = require("mongodb").MongoClient

const PORT = process.env.PORT || 3000
const URL = process.env.MONGODB_URI || "mongodb://localhost:27017"

app.use(express.json())

mongoClient.connect(URL, { useUnifiedTopology: true }, (err, db) => {
    if (err) {
        console.log("Error connecting to MongoClient!")
    } else {
        const myDb = db.db("myDb")
        const collection = myDb.collection("postsTable")

        app.post("/add", (req, res) => {
            const userPost = {
                _id: req.body._id,
                title: req.body.title,
                content: req.body.content,
                author: req.body.author
            }

            collection.insertOne(userPost, (err, result) => {
                if (err) {
                    console.log(err.message)
                    res.status(400).send(err.message)
                } else {
                    res.status(200).send()
                }
            })
        })

        app.put("/update", (req, res) => {
            const query = {
                _id: req.body._id
            }
            const updatedPost = {
                $set: {
                    title: req.body.title,
                    content: req.body.content,
                    author: req.body.author
                }
            }

            collection.updateOne(query, updatedPost, (err, result) => {
                if (err) {
                    console.log(err.message)
                    res.status(400).send(err.message)
                } else {
                    res.status(200).send()
                }
            })
        })

        app.delete("/delete", (req, res)=>{
            const query={
                _id:req.body._id
            }

            collection.deleteOne(query, (err, result)=>{
                if (err) {
                    console.log(err.message)
                    res.status(400).send(err.message)
                } else {
                    res.status(200).send()
                }
            })
        })

        app.get("/posts", (req, res) => {
            collection.find({}).toArray((err, result) => {
                if (err) {
                    res.status(400).send(err.message)
                } else {
                    res.status(200).send(JSON.stringify(result))
                }
            })
        })
    }
})

app.listen(PORT)