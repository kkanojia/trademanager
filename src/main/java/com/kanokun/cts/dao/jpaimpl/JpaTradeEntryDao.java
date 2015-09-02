package com.kanokun.cts.dao.jpaimpl;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.kanokun.cts.dao.JpaDao;
import com.kanokun.cts.dao.TradeEntryDao;
import com.kanokun.cts.entity.TradeEntry;

public class JpaTradeEntryDao extends JpaDao<TradeEntry, Long> implements TradeEntryDao {

	public JpaTradeEntryDao() {
		super(TradeEntry.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TradeEntry> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<TradeEntry> criteriaQuery = builder.createQuery(TradeEntry.class);

		Root<TradeEntry> root = criteriaQuery.from(TradeEntry.class);
		criteriaQuery.orderBy(builder.asc(root.get("id")));

		TypedQuery<TradeEntry> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeEntry> getAllNonSettledEntry(Integer assetCode, Date date) {
		return getEntityManager()
				.createQuery(
						"SELECT t FROM TradeEntry t WHERE t.buySell  = :buy and t.settled = :settled and t.assetId = :assetid and t.tradeDate < :date")
				.setParameter("buy", "Buy").setParameter("settled", false).setParameter("assetid", assetCode).setParameter("date", date)
				.getResultList();
	}

	@Override
	@Transactional()
	public int deleteAll() {
		return getEntityManager().createQuery("DELETE FROM TradeEntry").executeUpdate();

	}

}
