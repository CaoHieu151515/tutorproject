import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './user-verify-my-suffix.reducer';

export const UserVerifyMySuffix = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const userVerifyList = useAppSelector(state => state.userVerify.entities);
  const loading = useAppSelector(state => state.userVerify.loading);

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
      <h2 id="user-verify-my-suffix-heading" data-cy="UserVerifyHeading">
        <Translate contentKey="projectApp.userVerify.home.title">User Verifies</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="projectApp.userVerify.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/user-verify-my-suffix/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="projectApp.userVerify.home.createLabel">Create new User Verify</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {userVerifyList && userVerifyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="projectApp.userVerify.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('rating')}>
                  <Translate contentKey="projectApp.userVerify.rating">Rating</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('rating')} />
                </th>
                <th className="hand" onClick={sort('school')}>
                  <Translate contentKey="projectApp.userVerify.school">School</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('school')} />
                </th>
                <th className="hand" onClick={sort('studentID')}>
                  <Translate contentKey="projectApp.userVerify.studentID">Student ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('studentID')} />
                </th>
                <th className="hand" onClick={sort('major')}>
                  <Translate contentKey="projectApp.userVerify.major">Major</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('major')} />
                </th>
                <th className="hand" onClick={sort('graduationYear')}>
                  <Translate contentKey="projectApp.userVerify.graduationYear">Graduation Year</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('graduationYear')} />
                </th>
                <th>
                  <Translate contentKey="projectApp.userVerify.identityCard">Identity Card</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {userVerifyList.map((userVerify, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/user-verify-my-suffix/${userVerify.id}`} color="link" size="sm">
                      {userVerify.id}
                    </Button>
                  </td>
                  <td>{userVerify.rating}</td>
                  <td>{userVerify.school}</td>
                  <td>{userVerify.studentID}</td>
                  <td>{userVerify.major}</td>
                  <td>{userVerify.graduationYear}</td>
                  <td>
                    {userVerify.identityCard ? (
                      <Link to={`/identity-card-my-suffix/${userVerify.identityCard.id}`}>{userVerify.identityCard.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/user-verify-my-suffix/${userVerify.id}`}
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
                        to={`/user-verify-my-suffix/${userVerify.id}/edit`}
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
                        onClick={() => (window.location.href = `/user-verify-my-suffix/${userVerify.id}/delete`)}
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
              <Translate contentKey="projectApp.userVerify.home.notFound">No User Verifies found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default UserVerifyMySuffix;
