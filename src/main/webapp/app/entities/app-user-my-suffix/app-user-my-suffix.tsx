import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './app-user-my-suffix.reducer';

export const AppUserMySuffix = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const appUserList = useAppSelector(state => state.appUser.entities);
  const loading = useAppSelector(state => state.appUser.loading);

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
      <h2 id="app-user-my-suffix-heading" data-cy="AppUserHeading">
        <Translate contentKey="projectApp.appUser.home.title">App Users</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="projectApp.appUser.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/app-user-my-suffix/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="projectApp.appUser.home.createLabel">Create new App User</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {appUserList && appUserList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="projectApp.appUser.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('beTutor')}>
                  <Translate contentKey="projectApp.appUser.beTutor">Be Tutor</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('beTutor')} />
                </th>
                <th className="hand" onClick={sort('gender')}>
                  <Translate contentKey="projectApp.appUser.gender">Gender</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('gender')} />
                </th>
                <th className="hand" onClick={sort('bankAccountNumber')}>
                  <Translate contentKey="projectApp.appUser.bankAccountNumber">Bank Account Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('bankAccountNumber')} />
                </th>
                <th className="hand" onClick={sort('bankName')}>
                  <Translate contentKey="projectApp.appUser.bankName">Bank Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('bankName')} />
                </th>
                <th className="hand" onClick={sort('walletAddress')}>
                  <Translate contentKey="projectApp.appUser.walletAddress">Wallet Address</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('walletAddress')} />
                </th>
                <th>
                  <Translate contentKey="projectApp.appUser.tutor">Tutor</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="projectApp.appUser.userVerify">User Verify</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="projectApp.appUser.user">User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="projectApp.appUser.rating">Rating</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {appUserList.map((appUser, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/app-user-my-suffix/${appUser.id}`} color="link" size="sm">
                      {appUser.id}
                    </Button>
                  </td>
                  <td>{appUser.beTutor ? 'true' : 'false'}</td>
                  <td>
                    <Translate contentKey={`projectApp.GenderType.${appUser.gender}`} />
                  </td>
                  <td>{appUser.bankAccountNumber}</td>
                  <td>{appUser.bankName}</td>
                  <td>{appUser.walletAddress}</td>
                  <td>{appUser.tutor ? <Link to={`/tutor-my-suffix/${appUser.tutor.id}`}>{appUser.tutor.id}</Link> : ''}</td>
                  <td>
                    {appUser.userVerify ? <Link to={`/user-verify-my-suffix/${appUser.userVerify.id}`}>{appUser.userVerify.id}</Link> : ''}
                  </td>
                  <td>{appUser.user ? appUser.user.id : ''}</td>
                  <td>{appUser.rating ? <Link to={`/rating-my-suffix/${appUser.rating.id}`}>{appUser.rating.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/app-user-my-suffix/${appUser.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/app-user-my-suffix/${appUser.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/app-user-my-suffix/${appUser.id}/delete`)}
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
              <Translate contentKey="projectApp.appUser.home.notFound">No App Users found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default AppUserMySuffix;
