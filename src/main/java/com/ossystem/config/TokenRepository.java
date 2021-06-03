package com.ossystem.config;

import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public class TokenRepository extends JdbcDaoSupport implements PersistentTokenRepository {

  public TokenRepository() {

  }

  @Override
  public void createNewToken(PersistentRememberMeToken token) {
    if (this.getJdbcTemplate() != null) {
      this.getJdbcTemplate().update("insert into user_token (username, series, token, last_used) values(?,?,?,?)",
          token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate());
    }

  }

  @Override
  public void updateToken(String series, String tokenValue, Date lastUsed) {
    if (this.getJdbcTemplate() != null) {
      this.getJdbcTemplate().update("update user_token set token = ?, last_used = ? where series = ?", tokenValue,
          lastUsed, series);
    }
  }

  @Override
  public PersistentRememberMeToken getTokenForSeries(String seriesId) {
    try {
      if (this.getJdbcTemplate() != null) {
        return this.getJdbcTemplate().queryForObject(
            "select username,series,token,last_used from user_token where series = ?",
            (rs, rowNum) -> new PersistentRememberMeToken(rs.getString(1), rs.getString(2), rs.getString(3),
                rs.getTimestamp(4)),
            seriesId);
      }
    } catch (EmptyResultDataAccessException exception1) {
      if (this.logger.isDebugEnabled())
        this.logger.debug(" Series '" + seriesId + "' The Token did not return any results.", exception1);
    } catch (IncorrectResultSizeDataAccessException exception2) {
      this.logger.error("series '" + seriesId + "'The Token returns multiple values, but the series should be unique.",
          exception2);
    } catch (DataAccessException exception3) {
      this.logger.error("series '" + seriesId + "' Load the token.", exception3);
    }
    return null;
  }

  @Override
  public void removeUserTokens(String username) {
    if (this.getJdbcTemplate() != null) {
      this.getJdbcTemplate().update("delete from user_token where username = ?", username);
    }

  }

}
