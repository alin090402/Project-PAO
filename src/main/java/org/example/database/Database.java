package org.example.database;

import org.example.utils.Audit;
import org.example.utils.ConfigLoader;

import java.sql.*;
import java.util.Properties;

public class Database {

    private final String connString;
    private final String username;
    private final String password;
    private Connection connection;
    private static Database instance;

    private Database() {
        ConfigLoader configLoader = ConfigLoader.getInstance();
        Properties appProp = configLoader.getAppProp();
        connString = appProp.getProperty("connectionString");
        username = appProp.getProperty("username");
        password = appProp.getProperty("password");
    }

    public static Database getInstance() {
        if (instance == null)
        {
            instance = new Database();
        }
        return instance;
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(connString, username, password);
    }

    public void init() throws SQLException {
        connect();
        if (connection != null) {
            Audit.getInstance().logInfo("Connected to database");
        }
        createTables();
    }

    private void executeSql(String sql) throws SQLException {
        try {
            Audit.getInstance().logInfo("Executing SQL: " + sql);
            connection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            Audit.getInstance().logError("Could not execute SQL: " + sql + "\n" + e.getStackTrace().toString());
            throw e;
        }
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        try {
            Audit.getInstance().logInfo("Executing SQL: " + sql);
            return connection.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            Audit.getInstance().logError("Could not execute SQL: " + sql + "\n" + e.getStackTrace().toString());
            throw e;
        }
    }

    public ResultSet executePreparedQuery(PreparedStatement statement) throws SQLException {
        try{
            Audit.getInstance().logInfo("Executing prepared statement: " + statement.toString());
            return statement.executeQuery();
        } catch (SQLException e) {
            Audit.getInstance().logError("Could not execute prepared statement: " + statement.toString());
            throw e;
        }
    }
    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    public void executePrepared(PreparedStatement statement) throws SQLException {
        try{
            statement.execute();
            Audit.getInstance().logInfo("Executing prepared statement: " + statement.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            Audit.getInstance().logError("Could not execute prepared statement: " + statement.toString());
            throw e;
        }
    }

    private void createTables() throws SQLException {
        // Create tables if they don't exist
        // user
        String sqlUser =
                "CREATE TABLE IF NOT EXISTS `users` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `username` varchar(255) NOT NULL,\n" +
                "  `password` varchar(255) NOT NULL DEFAULT '123456',\n" +
                "  `role` varchar(255) NOT NULL,\n" +
                "  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "                PRIMARY KEY (`id`)\n" +
                ");";
        executeSql(sqlUser);
        // userPremium
        String sqlUserPremium =
                "CREATE TABLE IF NOT EXISTS `users_premium` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `user_id` int(11) NOT NULL,\n" +
                "  `expirationDate` datetime NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `user_id` (`user_id`),\n" +
                "  CONSTRAINT `user_premium_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)\n" +
                ");";
        executeSql(sqlUserPremium);

        // recipe
        String sqlRecipe =
                "CREATE TABLE iF NOT EXISTS `recipes`(\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(255) NOT NULL,\n" +
                "  `description` varchar(255) NOT NULL,\n" +
                "  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ");";
        executeSql(sqlRecipe);

        // ingredient
        String sqlIngredient =
                "CREATE TABLE IF NOT EXISTS `ingredients` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(255) NOT NULL,\n" +
                "  `carbohydrates` double NOT NULL,\n" +
                "  `proteins` double NOT NULL,\n" +
                "  `fats` double NOT NULL,\n" +
                "  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ");";
        executeSql(sqlIngredient);

        // inventory
        String sqlInventory =
                "CREATE TABLE IF NOT EXISTS `inventory` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `user_id` int(11) NOT NULL,\n" +
                "  `ingredient_id` int(11) NOT NULL,\n" +
                "  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `user_id` (`user_id`),\n" +
                "  KEY `ingredient_id` (`ingredient_id`),\n" +
                "  CONSTRAINT `inventory_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),\n" +
                "  CONSTRAINT `inventory_ibfk_2` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredients` (`id`)\n" +
                ");";
        executeSql(sqlInventory);

        // recipeIngredient
        String sqlRecipeIngredient =
                "CREATE TABLE IF NOT EXISTS `recipe_ingredient` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `recipe_id` int(11) NOT NULL,\n" +
                "  `ingredient_id` int(11) NOT NULL,\n" +
                "  `quantity` double NOT NULL,\n" +
                "  `unit` varchar(255) NOT NULL,\n" +
                "  `importance` varchar(255) NOT NULL,\n" +
                "  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `recipe_id` (`recipe_id`),\n" +
                "  KEY `ingredient_id` (`ingredient_id`),\n" +
                "  CONSTRAINT `recipe_ingredient_ibfk_1` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`id`),\n" +
                "  CONSTRAINT `recipe_ingredient_ibfk_2` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredients` (`id`)\n" +
                ");";
        executeSql(sqlRecipeIngredient);

        String sqlFavoriteRecipes = "CREATE TABLE IF NOT EXISTS `favorite_recipes` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `user_id` int(11) NOT NULL,\n" +
                "  `recipe_id` int(11) NOT NULL,\n" +
                "  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `user_id` (`user_id`),\n" +
                "  KEY `recipe_id` (`recipe_id`),\n" +
                "  CONSTRAINT `favorite_recipes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),\n" +
                "  CONSTRAINT `favorite_recipes_ibfk_2` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`id`)\n" +
                ");";
        executeSql(sqlFavoriteRecipes);


    }


}
