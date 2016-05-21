package ua.kpi.nc.persistence.dao.impl;

import ua.kpi.nc.persistence.dao.DaoUtil;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;

/**
 * Created by IO on 21.05.2016.
 */
public class DaoUtilImpl extends JdbcDaoSupport implements DaoUtil {


    private static final String SQL_CONNECTION_TEST = "SELECT VERSION()";

    public DaoUtilImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    private ResultSetExtractor<String> extractor = resultSet -> resultSet.getString("version");

    @Override
    public boolean checkConnection() {
        return null != this.getJdbcTemplate().queryWithParameters(SQL_CONNECTION_TEST, extractor);
    }
}
