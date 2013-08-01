package au.net.huni.test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.Parameter;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

// Dummy implementation to be used in tests involving Roo dynamic finders.

public class DummyTypedQuery<T> implements TypedQuery<T> {

	private T singleResult;
	private List<T> resultList;
	private boolean isThrowException = false;

	public DummyTypedQuery(boolean isThrowException) {
		this.isThrowException = isThrowException;
	}

	public DummyTypedQuery(T singleResult) {
		this.singleResult = singleResult;
	}

	public DummyTypedQuery(List<T> resultList) {
		this.resultList = resultList;
	}

	public DummyTypedQuery() {

	}

	@Override
	public List<T> getResultList() {
		return resultList;
	}

	@Override
	public T getSingleResult() {
		if (isThrowException) {
			throw new NoResultException();
		}
		return singleResult;
	}

	// ===================================
	// Below here not used.
	@Override
	public int executeUpdate() {
		// Not used
		return 0;
	}

	@Override
	public int getMaxResults() {
		// Not used
		return 0;
	}

	@Override
	public int getFirstResult() {
		// Not used
		return 0;
	}

	@Override
	public Map<String, Object> getHints() {
		// Not used
		return null;
	}

	@Override
	public Set<Parameter<?>> getParameters() {
		// Not used
		return null;
	}

	@Override
	public Parameter<?> getParameter(String name) {
		// Not used
		return null;
	}

	@Override
	public <X> Parameter<X> getParameter(String name, Class<X> type) {
		// Not used
		return null;
	}

	@Override
	public Parameter<?> getParameter(int position) {
		// Not used
		return null;
	}

	@Override
	public <X> Parameter<X> getParameter(int position, Class<X> type) {
		// Not used
		return null;
	}

	@Override
	public boolean isBound(Parameter<?> param) {
		// Not used
		return false;
	}

	@Override
	public <X> X getParameterValue(Parameter<X> param) {
		// Not used
		return null;
	}

	@Override
	public Object getParameterValue(String name) {
		// Not used
		return null;
	}

	@Override
	public Object getParameterValue(int position) {
		// Not used
		return null;
	}

	@Override
	public FlushModeType getFlushMode() {
		// Not used
		return null;
	}

	@Override
	public LockModeType getLockMode() {
		// Not used
		return null;
	}

	@Override
	public <X> X unwrap(Class<X> cls) {
		// Not used
		return null;
	}

	@Override
	public TypedQuery<T> setMaxResults(int maxResult) {
		// Not used
		return null;
	}

	@Override
	public TypedQuery<T> setFirstResult(int startPosition) {
		// Not used
		return null;
	}

	@Override
	public TypedQuery<T> setHint(String hintName, Object value) {
		// Not used
		return null;
	}

	@Override
	public TypedQuery<T> setParameter(Parameter<Calendar> param,
			Calendar value, TemporalType temporalType) {
		// Not used
		return null;
	}

	@Override
	public TypedQuery<T> setParameter(Parameter<Date> param, Date value,
			TemporalType temporalType) {
		// Not used
		return null;
	}

	@Override
	public TypedQuery<T> setParameter(String name, Object value) {
		// Not used
		return null;
	}

	@Override
	public TypedQuery<T> setParameter(String name, Calendar value,
			TemporalType temporalType) {
		// Not used
		return null;
	}

	@Override
	public TypedQuery<T> setParameter(String name, Date value,
			TemporalType temporalType) {
		// Not used
		return null;
	}

	@Override
	public TypedQuery<T> setParameter(int position, Object value) {
		// Not used
		return null;
	}

	@Override
	public TypedQuery<T> setParameter(int position, Calendar value,
			TemporalType temporalType) {
		// Not used
		return null;
	}

	@Override
	public TypedQuery<T> setParameter(int position, Date value,
			TemporalType temporalType) {
		// Not used
		return null;
	}

	@Override
	public TypedQuery<T> setFlushMode(FlushModeType flushMode) {
		// Not used
		return null;
	}

	@Override
	public TypedQuery<T> setLockMode(LockModeType lockMode) {
		// Not used
		return null;
	}

	@Override
	public <X> TypedQuery<T> setParameter(Parameter<X> param, X value) {
		return null;
	}

}
