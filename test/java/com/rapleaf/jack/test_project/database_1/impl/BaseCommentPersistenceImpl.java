
/**
 * Autogenerated by Jack
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.rapleaf.jack.test_project.database_1.impl;

import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;

import com.rapleaf.jack.AbstractDatabaseModel;
import com.rapleaf.jack.BaseDatabaseConnection;
import com.rapleaf.jack.queries.where_operators.IWhereOperator;
import com.rapleaf.jack.queries.WhereConstraint;
import com.rapleaf.jack.queries.WhereClause;
import com.rapleaf.jack.queries.ModelQuery;
import com.rapleaf.jack.ModelWithId;
import com.rapleaf.jack.test_project.database_1.iface.ICommentPersistence;
import com.rapleaf.jack.test_project.database_1.models.Comment;
import com.rapleaf.jack.test_project.database_1.query.CommentQueryBuilder;
import com.rapleaf.jack.test_project.database_1.query.CommentDeleteBuilder;


import com.rapleaf.jack.test_project.IDatabases;

public class BaseCommentPersistenceImpl extends AbstractDatabaseModel<Comment> implements ICommentPersistence {
  private final IDatabases databases;

  public BaseCommentPersistenceImpl(BaseDatabaseConnection conn, IDatabases databases) {
    super(conn, "comments", Arrays.<String>asList("content", "commenter_id", "commented_on_id", "created_at"));
    this.databases = databases;
  }

  @Override
  public Comment create(Map<Enum, Object> fieldsMap) throws IOException {
    String content = (String) fieldsMap.get(Comment._Fields.content);
    int commenter_id = (Integer) fieldsMap.get(Comment._Fields.commenter_id);
    long commented_on_id = (Long) fieldsMap.get(Comment._Fields.commented_on_id);
    Long created_at_tmp = (Long) fieldsMap.get(Comment._Fields.created_at);
    long created_at = created_at_tmp == null ? 0L : created_at_tmp;
    return create(content, commenter_id, commented_on_id, created_at);
  }

  public Comment create(final String content, final int commenter_id, final long commented_on_id) throws IOException {
    return this.create(content, commenter_id, commented_on_id, System.currentTimeMillis());
  }
  public Comment create(final String content, final int commenter_id, final long commented_on_id, final long created_at) throws IOException {
    long __id = realCreate(new AttrSetter() {
      public void set(PreparedStatement stmt) throws SQLException {
        if (content == null) {
          stmt.setNull(1, java.sql.Types.CHAR);
        } else {
          stmt.setString(1, content);
        }
          stmt.setInt(2, commenter_id);
          stmt.setLong(3, commented_on_id);
          stmt.setTimestamp(4, new Timestamp(created_at));
      }
    }, getInsertStatement(Arrays.<String>asList("content", "commenter_id", "commented_on_id", "created_at")));
    Comment newInst = new Comment(__id, content, commenter_id, commented_on_id, created_at, databases);
    newInst.setCreated(true);
    cachedById.put(__id, newInst);
    clearForeignKeyCache();
    return newInst;
  }


  public Comment create(final int commenter_id, final long commented_on_id, final long created_at) throws IOException {
    long __id = realCreate(new AttrSetter() {
      public void set(PreparedStatement stmt) throws SQLException {
          stmt.setInt(1, commenter_id);
          stmt.setLong(2, commented_on_id);
          stmt.setTimestamp(3, new Timestamp(created_at));
      }
    }, getInsertStatement(Arrays.<String>asList("commenter_id", "commented_on_id", "created_at")));
    Comment newInst = new Comment(__id, null, commenter_id, commented_on_id, created_at, databases);
    newInst.setCreated(true);
    cachedById.put(__id, newInst);
    clearForeignKeyCache();
    return newInst;
  }


  public Comment createDefaultInstance() throws IOException {
    return create(0, 0L, 0L);
  }

  public List<Comment> find(Map<Enum, Object> fieldsMap) throws IOException {
    return find(null, fieldsMap);
  }

  public List<Comment> find(Set<Long> ids, Map<Enum, Object> fieldsMap) throws IOException {
    List<Comment> foundList = new ArrayList<Comment>();

    if (fieldsMap == null || fieldsMap.isEmpty()) {
      return foundList;
    }

    StringBuilder statementString = new StringBuilder();
    statementString.append("SELECT * FROM comments WHERE (");
    List<Object> nonNullValues = new ArrayList<Object>();
    List<Comment._Fields> nonNullValueFields = new ArrayList<Comment._Fields>();

    Iterator<Map.Entry<Enum, Object>> iter = fieldsMap.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry<Enum, Object> entry = iter.next();
      Enum field = entry.getKey();
      Object value = entry.getValue();

      String queryValue = value != null ? " = ? " : " IS NULL";
      if (value != null) {
        nonNullValueFields.add((Comment._Fields) field);
        nonNullValues.add(value);
      }

      statementString.append(field).append(queryValue);
      if (iter.hasNext()) {
        statementString.append(" AND ");
      }
    }
    if (ids != null) statementString.append(" AND ").append(getIdSetCondition(ids));
    statementString.append(")");

    int retryCount = 0;
    PreparedStatement preparedStatement;

    while (true) {
      preparedStatement = getPreparedStatement(statementString.toString());

      for (int i = 0; i < nonNullValues.size(); i++) {
        Comment._Fields field = nonNullValueFields.get(i);
        try {
          switch (field) {
            case content:
              preparedStatement.setString(i+1, (String) nonNullValues.get(i));
              break;
            case commenter_id:
              preparedStatement.setInt(i+1, (Integer) nonNullValues.get(i));
              break;
            case commented_on_id:
              preparedStatement.setLong(i+1, (Long) nonNullValues.get(i));
              break;
            case created_at:
              preparedStatement.setTimestamp(i+1, new Timestamp((Long) nonNullValues.get(i)));
              break;
          }
        } catch (SQLException e) {
          throw new IOException(e);
        }
      }

      try {
        executeQuery(foundList, preparedStatement);
        return foundList;
      } catch (SQLRecoverableException e) {
        if (++retryCount > AbstractDatabaseModel.MAX_CONNECTION_RETRIES) {
          throw new IOException(e);
        }
      } catch (SQLException e) {
        throw new IOException(e);
      }
    }
  }

  @Override
  protected void setStatementParameters(PreparedStatement preparedStatement, WhereClause whereClause) throws IOException {
    int index = 0;
    for (WhereConstraint constraint : whereClause.getWhereConstraints()) {
      for (Object parameter : constraint.getParameters()) {
        if (parameter == null) {
          continue;
        }
        try {
          if (constraint.isId()) {
            preparedStatement.setLong(++index, (Long)parameter);
          } else {
            Comment._Fields field = (Comment._Fields)constraint.getField();
            switch (field) {
              case content:
                preparedStatement.setString(++index, (String) parameter);
                break;
              case commenter_id:
                preparedStatement.setInt(++index, (Integer) parameter);
                break;
              case commented_on_id:
                preparedStatement.setLong(++index, (Long) parameter);
                break;
              case created_at:
                preparedStatement.setTimestamp(++index, new Timestamp((Long) parameter));
                break;
            }
          }
        } catch (SQLException e) {
          throw new IOException(e);
        }
      }
    }
  }

  @Override
  protected void setAttrs(Comment model, PreparedStatement stmt) throws SQLException {
    if (model.getContent() == null) {
      stmt.setNull(1, java.sql.Types.CHAR);
    } else {
      stmt.setString(1, model.getContent());
    }
    {
      stmt.setInt(2, model.getCommenterId());
    }
    {
      stmt.setLong(3, model.getCommentedOnId());
    }
    {
      stmt.setTimestamp(4, new Timestamp(model.getCreatedAt()));
    }
    stmt.setLong(5, model.getId());
  }

  @Override
  protected Comment instanceFromResultSet(ResultSet rs, Set<Enum> selectedFields) throws SQLException {
    boolean allFields = selectedFields == null || selectedFields.isEmpty();
    long id = rs.getLong("id");
    return new Comment(id,
      allFields || selectedFields.contains(Comment._Fields.content) ? rs.getString("content") : null,
      allFields || selectedFields.contains(Comment._Fields.commenter_id) ? getIntOrNull(rs, "commenter_id") : 0,
      allFields || selectedFields.contains(Comment._Fields.commented_on_id) ? getLongOrNull(rs, "commented_on_id") : 0L,
      allFields || selectedFields.contains(Comment._Fields.created_at) ? getDateAsLong(rs, "created_at") : 0L,
      databases
    );
  }

  public List<Comment> findByContent(final String value) throws IOException {
    return find(new HashMap<Enum, Object>(){{put(Comment._Fields.content, value);}});
  }

  public List<Comment> findByCommenterId(final int value) throws IOException {
    return find(new HashMap<Enum, Object>(){{put(Comment._Fields.commenter_id, value);}});
  }

  public List<Comment> findByCommentedOnId(final long value) throws IOException {
    return find(new HashMap<Enum, Object>(){{put(Comment._Fields.commented_on_id, value);}});
  }

  public List<Comment> findByCreatedAt(final long value) throws IOException {
    return find(new HashMap<Enum, Object>(){{put(Comment._Fields.created_at, value);}});
  }

  public CommentQueryBuilder query() {
    return new CommentQueryBuilder(this);
  }

  public CommentDeleteBuilder delete() {
    return new CommentDeleteBuilder(this);
  }
}
