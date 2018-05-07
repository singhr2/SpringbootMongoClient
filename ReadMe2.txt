Comparison Query operators
--------------------------
$gt
$lt
$gte
$lte    <- less than or equal to
$ne <- not equal to
$in <- in
$nin  <- not in

Logical Query operators
--------------------------
$and
$or
$not
$nor

>>> null handling
--------------------------
Given these inserts:
    db.test.insert({"num":1, "check":"check value"});
    db.test.insert({"num":2, "check":null});
    db.test.insert({"num":3});

This will return all three documents:
    db.test.find();

This will return the first and second documents only:
    db.test.find({"check":{$exists:true}});

This will return the first document only:
    db.test.find({"check":{$ne:null}});

This will return the second and third documents only:
    db.test.find({"check":null})

