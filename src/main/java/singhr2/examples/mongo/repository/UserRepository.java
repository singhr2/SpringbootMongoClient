package singhr2.examples.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import singhr2.examples.mongo.client.User;

import java.util.List;

/**
 * There are two approaches through which we can connect to MongoDB database -
 *      MongoRepository and MongoTemplate.
 *
 * Out-of-the-box, MongoRepository interface comes with many operations,
 * including standard CRUD operations (create-read-update-delete).
 */
public interface UserRepository extends MongoRepository<User, String> {
    // You can define other queries as needed by simply declaring their method signature.
    public User findByUsername(String firstName);

    //public List<User> findAllWithPassword(String password);
}
