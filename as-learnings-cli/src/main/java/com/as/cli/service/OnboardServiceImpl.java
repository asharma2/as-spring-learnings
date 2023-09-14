package com.as.cli.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OnboardServiceImpl implements OnboardService {

	@Autowired
	JdbcTemplate pushCoreJdbcTemplate;
	@Value("#{dhiscoSqlQry['SQL_PROPERTY_RATE_COUNT']}")
	String SQL_PROPERTY_RATE_COUNT;
	@Value("#{dhiscoSqlQry['SQL_UPDATE_MAXNRCS']}")
	String SQL_UPDATE_MAXNRCS;

	@Value("#{dhiscoSqlQry['SQL_SUPPLIERS_BY_CODE']}")
	String SQL_SUPPLIERS_BY_CODE;;

	@Value("#{dhiscoSqlQry['SQL_BRAND_BY_CODE']}")
	String SQL_BRAND_BY_CODE;
	@Value("#{dhiscoSqlQry['SQL_INSERT_BRAND']}")
	String SQL_INSERT_BRAND;

	@Value("#{dhiscoSqlQry['SQL_GET_PROPERTY']}")
	String SQL_GET_PROPERTY;
	@Value("#{dhiscoSqlQry['SQL_INSERT_PROPERTY']}")
	String SQL_INSERT_PROPERTY;

	@Value("#{dhiscoSqlQry['SQL_INSERT_SGA']}")
	String SQL_INSERT_SGA;
	@Value("#{dhiscoSqlQry['SQL_SELECT_SGA']}")
	String SQL_SELECT_SGA;

	@Value("#{dhiscoSqlQry['SQL_GET_SHOP_PROFILE']}")
	String SQL_GET_SHOP_PROFILE;

	@Value("#{dhiscoSqlQry['SQL_UPDATE_SHOP_PROFILE']}")
	String SQL_UPDATE_SHOP_PROFILE;

	@Value("#{dhiscoSqlQry['SQL_INSERT_SHOP_PROFILE']}")
	String SQL_INSERT_SHOP_PROFILE;

	@Value("#{dhiscoSqlQry['SQL_SELECT_SHOP_PROFILE_NRCS']}")
	String SQL_SELECT_SHOP_PROFILE_NRCS;
	@Value("#{dhiscoSqlQry['SQL_INSERT_SHOP_PROFILE_NRCS']}")
	String SQL_INSERT_SHOP_PROFILE_NRCS;

	@Value("#{dhiscoSqlQry['SQL_SELECT_SHOP_PROFILE_GUESTS']}")
	String SQL_SELECT_SHOP_PROFILE_GUESTS;

	@Value("#{dhiscoSqlQry['SQL_INSERT_SHOP_PROFILE_GUESTS']}")
	String SQL_INSERT_SHOP_PROFILE_GUESTS;

	@Value("#{dhiscoSqlQry['SQL_SELECT_NRC']}")
	String SQL_SELECT_NRC;
	@Value("#{dhiscoSqlQry['SQL_INSERT_NRC']}")
	String SQL_INSERT_NRC;

	@Value("#{dhiscoSqlQry['SQL_SELECT_CHANNEL']}")
	String SQL_SELECT_CHANNEL;

	@Value("#{dhiscoSqlQry['SQL_GROUP_LIST']}")
	String SQL_GROUP_LIST;

	@Value("#{dhiscoSqlQry['SQL_ALL_PID_BY_SGA_BRAND']}")
	String SQL_ALL_PID_BY_SGA_BRAND;

	@Value("#{dhiscoSqlQry['SQL_DELETE_SHOP_PROFILE_GUESTS']}")
	String SQL_DELETE_SHOP_PROFILE_GUESTS;

	@Value("#{dhiscoSqlQry['SQL_DELETE_SHOP_PROFILE_RATES']}")
	String SQL_DELETE_SHOP_PROFILE_RATES;

	@Value("#{dhiscoSqlQry['SQL_DELETE_SHOP_PROFILE']}")
	String SQL_DELETE_SHOP_PROFILE;

	@Value("#{dhiscoSqlQry['SQL_SELECT_HOTEL_PROPERTY_SUBSCRIPTION']}")
	String SQL_SELECT_HOTEL_PROPERTY_SUBSCRIPTION;
	@Value("#{dhiscoSqlQry['SQL_INSERT_HOTEL_PROPERTY_SUBSCRIPTION']}")
	String SQL_INSERT_HOTEL_PROPERTY_SUBSCRIPTION;

	@Value("#{dhiscoSqlQry['SQL_SELECT_PROPERTY_GROUPS']}")
	String SQL_SELECT_PROPERTY_GROUPS;
	@Value("#{dhiscoSqlQry['SQL_INSERT_PROPERTY_GROUPS']}")
	String SQL_INSERT_PROPERTY_GROUPS;

	@Value("#{dhiscoSqlQry['SQL_SELECT_GROUPS']}")
	String SQL_SELECT_GROUPS;

	@Value("#{dhiscoSqlQry['SQL_DELETE_ADDL_GUEST_ROOM']}")
	String SQL_DELETE_ADDL_GUEST_ROOM;
	@Value("#{dhiscoSqlQry['SQL_DELETE_ADDL_GUEST_ROOM_ML']}")
	String SQL_DELETE_ADDL_GUEST_ROOM_ML;
	@Value("#{dhiscoSqlQry['SQL_DELETE_ADDL_ROOM_AMENITY']}")
	String SQL_DELETE_ADDL_ROOM_AMENITY;
	@Value("#{dhiscoSqlQry['SQL_UPDATE_MAXNRCS_BY_SGA_BRAND_PID']}")
	String SQL_UPDATE_MAXNRCS_BY_SGA_BRAND_PID;
	@Value("#{dhiscoSqlQry['SQL_HPCM_BY_PID_AND_CHANNEL']}")
	String SQL_HPCM_BY_PID_AND_CHANNEL;
	@Value("#{dhiscoSqlQry['SQL_INSERT_HPCM']}")
	String SQL_INSERT_HPCM;

	@Value("#{dhiscoSqlQry['SQL_SP_LIST_BY_SGA_BRAND_PID']}")
	String SQL_SP_LIST_BY_SGA_BRAND_PID;

	@Value("#{dhiscoSqlQry['SQL_BRAND_LIST']}")
	String SQL_BRAND_LIST;
	@Value("#{dhiscoSqlQry['SQL_SGA_LIST']}")
	String SQL_SGA_LIST;
	@Value("#{dhiscoSqlQry['SQL_SUPPLIERS_LIST']}")
	String SQL_SUPPLIERS_LIST;
	@Value("#{dhiscoSqlQry['SQL_CHANNEL_LIST']}")
	String SQL_CHANNEL_LIST;

	@Value("#{dhiscoSqlQry['SQL_HOTEL_LIST_BY_BRAND']}")
	String SQL_HOTEL_LIST_BY_BRAND;

	@Override
	public void onboardChoice(String brand, String pid, List<String> rates, Integer los, Integer maxnrcs) {
		log.info("Onboarding Started => Pid: {}", pid);
		try {
			List<Long> supplierIDs = pushCoreJdbcTemplate.queryForList(SQL_SUPPLIERS_BY_CODE, Long.class, "CHOICE");
			if (CollectionUtils.isEmpty(supplierIDs)) {
				log.warn("Supplier {} does not exists", "Choice");
				return;
			}

			List<Long> channelIDs = pushCoreJdbcTemplate.queryForList(SQL_SELECT_CHANNEL, Long.class, "AGODA_ZV");
			if (CollectionUtils.isEmpty(channelIDs)) {
				log.warn("Channel {} does not exists", "agoda_zv");
				return;
			}

			List<Long> groupIDs = pushCoreJdbcTemplate.queryForList(SQL_SELECT_GROUPS, Long.class, "AGODA-MEDIUM");
			if (CollectionUtils.isEmpty(groupIDs)) {
				log.warn("Group {} does not exists", "AGODA-MEDIUM");
				return;
			}

			List<Long> sgaIDs = pushCoreJdbcTemplate.queryForList(SQL_SELECT_SGA, Long.class, "ZV");
			if (CollectionUtils.isEmpty(groupIDs)) {
				log.warn("Sga {} does not exists", "ZV");
				return;
			}

			long supplierID = supplierIDs.stream().findFirst().get();
			long channelID = channelIDs.stream().findFirst().get();
			long groupID = groupIDs.stream().findFirst().get();
			long sgaID = sgaIDs.stream().findFirst().get();

			List<Long> brandIDs = pushCoreJdbcTemplate.queryForList(SQL_BRAND_BY_CODE, Long.class, brand, supplierID);

			if (CollectionUtils.isEmpty(brandIDs)) {
				log.warn("Brand {} does not exists", brand);
				return;
			}

			long brandID = brandIDs.stream().findFirst().get();

			Map<String, Long> rateIds = new HashMap<>();

			for (String rpc : rates) {
				List<Long> nrcIDs = pushCoreJdbcTemplate.queryForList(SQL_SELECT_NRC, Long.class, sgaID, rpc);
				if (CollectionUtils.isEmpty(nrcIDs)) {
					log.warn("Rate {} does not exists", rpc);
					return;
				}
				long nrcID = nrcIDs.stream().findAny().get();
				rateIds.put(rpc, nrcID);
			}

			List<Long> propertyIDs = pushCoreJdbcTemplate.queryForList(SQL_GET_PROPERTY, Long.class, pid, brandID);
			if (CollectionUtils.isEmpty(propertyIDs)) {
				KeyHolder keyHolder = new GeneratedKeyHolder();
				pushCoreJdbcTemplate.update(psc -> {
					PreparedStatement ps = psc.prepareStatement(SQL_INSERT_PROPERTY, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, pid);
					ps.setLong(2, brandID);
					return ps;
				}, keyHolder);
				propertyIDs.add(getLongValue(keyHolder.getKey()));
				log.info("Insert => Hotel: {}", pid);
			} else {
				log.info("Exists => Hotel: {}", pid);
			}

			long propertyID = propertyIDs.stream().findFirst().get();

			List<Long> profileIDs = pushCoreJdbcTemplate.queryForList(SQL_GET_SHOP_PROFILE, Long.class, sgaID, brandID,
					propertyID);
			if (CollectionUtils.isEmpty(profileIDs)) {
				KeyHolder keyHolder = new GeneratedKeyHolder();
				pushCoreJdbcTemplate.update(psc -> {
					PreparedStatement ps = psc.prepareStatement(SQL_INSERT_SHOP_PROFILE,
							Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, sgaID);
					ps.setLong(2, brandID);
					ps.setLong(3, propertyID);
					ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
					ps.setDate(5, new java.sql.Date(System.currentTimeMillis()));
					ps.setString(6, "REQUESTED_ONLY");
					ps.setInt(7, los);
					ps.setInt(8, maxnrcs);
					ps.setInt(9, 3);
					ps.setInt(10, 0);
					ps.setInt(11, 2);
					ps.setInt(12, 350);
					ps.setInt(13, 5);
					return ps;
				}, keyHolder);
				long spID = getLongValue(keyHolder.getKey());
				profileIDs.add(spID);
				log.info("Insert => Profile: {}", spID);
			}

			long profileID = profileIDs.stream().findFirst().get();

			rateIds.forEach((rpc, nrcID) -> {
				List<Long> brandProfileNrcIDs = pushCoreJdbcTemplate.queryForList(SQL_SELECT_SHOP_PROFILE_NRCS,
						Long.class, profileID, nrcID);
				if (CollectionUtils.isEmpty(brandProfileNrcIDs)) {
					pushCoreJdbcTemplate.update(psc -> {
						PreparedStatement ps = psc.prepareStatement(SQL_INSERT_SHOP_PROFILE_NRCS);
						ps.setLong(1, profileID);
						ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
						ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
						ps.setLong(4, nrcID);
						return ps;
					});
					log.info("Insert => RateProfile: {}", profileID);
				}
			});

			for (int adult = 1; adult <= 3; adult++) {
				List<Long> guestProfileIDs = pushCoreJdbcTemplate.queryForList(SQL_SELECT_SHOP_PROFILE_GUESTS,
						Long.class, profileID, adult, 0);
				int adultCount = adult;
				if (CollectionUtils.isEmpty(guestProfileIDs)) {
					pushCoreJdbcTemplate.update(psc -> {
						PreparedStatement ps = psc.prepareStatement(SQL_INSERT_SHOP_PROFILE_GUESTS,
								Statement.RETURN_GENERATED_KEYS);
						ps.setLong(1, profileID);
						ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
						ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
						ps.setInt(4, adultCount);
						ps.setInt(5, 0);
						ps.setString(6, null);
						return ps;
					});
					log.info("Insert => GuestProfile: {}", profileID);
				}
			}

			List<Long> subscriptionIDs = pushCoreJdbcTemplate.queryForList(SQL_SELECT_HOTEL_PROPERTY_SUBSCRIPTION,
					Long.class, channelID, propertyID);
			if (CollectionUtils.isEmpty(subscriptionIDs)) {
				pushCoreJdbcTemplate.update(psc -> {
					PreparedStatement ps = psc.prepareStatement(SQL_INSERT_HOTEL_PROPERTY_SUBSCRIPTION);
					ps.setLong(1, channelID);
					ps.setLong(2, propertyID);
					return ps;
				});
				log.info("Insert => Subscription: {}", pid);
			}

			List<Long> pgIDs = pushCoreJdbcTemplate.queryForList(SQL_SELECT_PROPERTY_GROUPS, Long.class, groupID,
					propertyID);
			if (pgIDs.isEmpty()) {
				pushCoreJdbcTemplate.update(psc -> {
					PreparedStatement ps = psc.prepareStatement(SQL_INSERT_PROPERTY_GROUPS);
					ps.setLong(1, groupID);
					ps.setLong(2, propertyID);
					return ps;
				});
				log.info("Insert => Group: {}", "AGODA-MEDIUM");
			}

			List<Long> hpcmIDs = pushCoreJdbcTemplate.queryForList(SQL_HPCM_BY_PID_AND_CHANNEL, Long.class, channelID,
					propertyID);
			if (CollectionUtils.isEmpty(hpcmIDs)) {
				pushCoreJdbcTemplate.update(psc -> {
					PreparedStatement ps = psc.prepareStatement(SQL_INSERT_HPCM);
					ps.setString(1, pid);
					ps.setString(2, "CREATED");
					ps.setLong(3, channelID);
					ps.setLong(4, propertyID);
					return ps;
				});
				log.info("Insert => Mapping: {}", pid);
			}

		} catch (

		Exception e) {
			log.error("Onboarding Failed => Pid: {}", pid, e);
		}
	}

	public Long getLongValue(Number keyV) {
		if (keyV instanceof BigDecimal)
			return keyV.longValue();
		if (keyV instanceof BigInteger)
			return keyV.longValue();
		return (Long) keyV;
	}
}
