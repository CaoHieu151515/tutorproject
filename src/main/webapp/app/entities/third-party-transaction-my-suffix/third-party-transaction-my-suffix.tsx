import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './third-party-transaction-my-suffix.reducer';

export const ThirdPartyTransactionMySuffix = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const thirdPartyTransactionList = useAppSelector(state => state.thirdPartyTransaction.entities);
  const loading = useAppSelector(state => state.thirdPartyTransaction.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="third-party-transaction-my-suffix-heading" data-cy="ThirdPartyTransactionHeading">
        <Translate contentKey="projectApp.thirdPartyTransaction.home.title">Third Party Transactions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="projectApp.thirdPartyTransaction.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/third-party-transaction-my-suffix/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="projectApp.thirdPartyTransaction.home.createLabel">Create new Third Party Transaction</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {thirdPartyTransactionList && thirdPartyTransactionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="projectApp.thirdPartyTransaction.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('thirdPartyId')}>
                  <Translate contentKey="projectApp.thirdPartyTransaction.thirdPartyId">Third Party Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('thirdPartyId')} />
                </th>
                <th className="hand" onClick={sort('transactionDate')}>
                  <Translate contentKey="projectApp.thirdPartyTransaction.transactionDate">Transaction Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('transactionDate')} />
                </th>
                <th>
                  <Translate contentKey="projectApp.thirdPartyTransaction.walletTransaction">Wallet Transaction</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {thirdPartyTransactionList.map((thirdPartyTransaction, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/third-party-transaction-my-suffix/${thirdPartyTransaction.id}`} color="link" size="sm">
                      {thirdPartyTransaction.id}
                    </Button>
                  </td>
                  <td>{thirdPartyTransaction.thirdPartyId}</td>
                  <td>
                    {thirdPartyTransaction.transactionDate ? (
                      <TextFormat type="date" value={thirdPartyTransaction.transactionDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {thirdPartyTransaction.walletTransaction ? (
                      <Link to={`/wallet-transaction-my-suffix/${thirdPartyTransaction.walletTransaction.id}`}>
                        {thirdPartyTransaction.walletTransaction.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/third-party-transaction-my-suffix/${thirdPartyTransaction.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/third-party-transaction-my-suffix/${thirdPartyTransaction.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/third-party-transaction-my-suffix/${thirdPartyTransaction.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="projectApp.thirdPartyTransaction.home.notFound">No Third Party Transactions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ThirdPartyTransactionMySuffix;
