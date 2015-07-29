
/**
 * Autogenerated by Jack
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.rapleaf.jack.test_project.database_1.impl;

import java.io.IOException;

import com.rapleaf.jack.test_project.database_1.IDatabase1;
import com.rapleaf.jack.queries.GenericQuery;
import com.rapleaf.jack.BaseDatabaseConnection;
import com.rapleaf.jack.test_project.database_1.iface.ICommentPersistence;
import com.rapleaf.jack.test_project.database_1.iface.IImagePersistence;
import com.rapleaf.jack.test_project.database_1.iface.IPostPersistence;
import com.rapleaf.jack.test_project.database_1.iface.IUserPersistence;

import com.rapleaf.jack.test_project.IDatabases;

public class Database1Impl implements IDatabase1 {
  
  private final BaseDatabaseConnection conn;
  private final IDatabases databases;
  private final ICommentPersistence comments;
  private final IImagePersistence images;
  private final IPostPersistence posts;
  private final IUserPersistence users;

  public Database1Impl(BaseDatabaseConnection conn, IDatabases databases) {
    this.conn = conn;
    this.databases = databases;
    this.comments = new BaseCommentPersistenceImpl(conn, databases);
    this.images = new BaseImagePersistenceImpl(conn, databases);
    this.posts = new BasePostPersistenceImpl(conn, databases);
    this.users = new BaseUserPersistenceImpl(conn, databases);
  }

  public GenericQuery.Builder createQuery() {
    return GenericQuery.create(conn);
  }

  public ICommentPersistence comments(){
    return comments;
  }

  public IImagePersistence images(){
    return images;
  }

  public IPostPersistence posts(){
    return posts;
  }

  public IUserPersistence users(){
    return users;
  }

  public boolean deleteAll() throws IOException {
    boolean success = true;
    try {
    success &= comments.deleteAll();
    success &= images.deleteAll();
    success &= posts.deleteAll();
    success &= users.deleteAll();
    } catch (IOException e) {
      throw e;
    }
    return success;
  }

  public void disableCaching() {
    comments.disableCaching();
    images.disableCaching();
    posts.disableCaching();
    users.disableCaching();
  }

  public void setAutoCommit(boolean autoCommit) {
    conn.setAutoCommit(autoCommit);
  }

  public boolean getAutoCommit() {
    return conn.getAutoCommit();
  }

  public void commit() {
    conn.commit();
  }

  public void rollback() {
    conn.rollback();
  }

  public void resetConnection() {
    conn.resetConnection();
  }

  public IDatabases getDatabases() {
    return databases;
  }

}