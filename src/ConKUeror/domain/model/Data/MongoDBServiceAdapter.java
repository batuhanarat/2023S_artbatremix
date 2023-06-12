package ConKUeror.domain.model.Data;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.google.gson.Gson;

import java.io.Serializable;

public class MongoDBServiceAdapter implements ISaveLoadAdapter {

	@Override
	public void save(GameState gameState) {
	    // Connect to MongoDB
		System.out.println("savedeyim");
        String uri = "mongodb+srv://gamedb:zQqJloEBJpAfbJ9J@cluster0.xvyeauj.mongodb.net/?retryWrites=true&w=majority";

	    MongoClient mongoClient = MongoClients.create(uri);
	    MongoDatabase database = mongoClient.getDatabase("gameDB");
	    MongoCollection<Document> collection = database.getCollection("gameState");

	
	    Document doc = Document.parse(new Gson().toJson(gameState));

	    collection.insertOne(doc);
	    System.out.println("Game saved online");
	}

	@Override
	public GameState load() {
		String uri = "mongodb+srv://gamedb:zQqJloEBJpAfbJ9J@cluster0.xvyeauj.mongodb.net/?retryWrites=true&w=majority";

	    MongoClient mongoClient = MongoClients.create(uri);
	    MongoDatabase database = mongoClient.getDatabase("gameDB");
	    MongoCollection<Document> collection = database.getCollection("gameState");

	    
	    Document doc = collection.find().sort(new Document("_id", -1)).first();

	
	    GameState gameState = new Gson().fromJson(doc.toJson(), GameState.class);

	    return gameState;
	}
}