package major_project.model.httpHandler;

public class DummyCryptoHttpHandler implements CryptoHttpHandler {
    

    @Override
    public String getListing(String key) {
        try {
            Thread.sleep(600);  
        } catch (Exception e) {}
          
        return """
            {
                \"status\": {
                    \"timestamp\": \"2022-05-01T10:22:56.567Z\",
                    \"error_code\": 0,
                    \"error_message\": null,
                    \"elapsed\": 27,
                    \"credit_count\": 1,
                    \"notice\": null,
                    \"total_count\": 10085
                },
                \"data\": [{
                        \"id\": 1,
                        \"name\": \"Bitcoin\",
                        \"symbol\": \"BTC\",
                        \"slug\": \"bitcoin\",
                        \"num_market_pairs\": 9379,
                        \"date_added\": \"2013-04-28T00:00:00.000Z\",
                        \"tags\": [\"mineable\", \"pow\", \"sha-256\", \"store-of-value\", \"state-channel\", \"coinbase-ventures-portfolio\", \"three-arrows-capital-portfolio\", \"polychain-capital-portfolio\", \"binance-labs-portfolio\", \"blockchain-capital-portfolio\", \"boostvc-portfolio\", \"cms-holdings-portfolio\", \"dcg-portfolio\", \"dragonfly-capital-portfolio\", \"electric-capital-portfolio\", \"fabric-ventures-portfolio\", \"framework-ventures-portfolio\", \"galaxy-digital-portfolio\", \"huobi-capital-portfolio\", \"alameda-research-portfolio\", \"a16z-portfolio\", \"1confirmation-portfolio\", \"winklevoss-capital-portfolio\", \"usv-portfolio\", \"placeholder-ventures-portfolio\", \"pantera-capital-portfolio\", \"multicoin-capital-portfolio\", \"paradigm-portfolio\"],
                        \"max_supply\": 21000000,
                        \"circulating_supply\": 19027400,
                        \"total_supply\": 19027400,
                        \"platform\": null,
                        \"cmc_rank\": 1,
                        \"self_reported_circulating_supply\": null,
                        \"self_reported_market_cap\": null,
                        \"last_updated\": \"2022-05-01T10:22:00.000Z\"
                    }, {
                        \"id\": 1027,
                        \"name\": \"Ethereum\",
                        \"symbol\": \"ETH\",
                        \"slug\": \"ethereum\",
                        \"num_market_pairs\": 5670,
                        \"date_added\": \"2015-08-07T00:00:00.000Z\",
                        \"tags\": [\"mineable\", \"pow\", \"smart-contracts\", \"ethereum-ecosystem\", \"coinbase-ventures-portfolio\", \"three-arrows-capital-portfolio\", \"polychain-capital-portfolio\", \"binance-labs-portfolio\", \"blockchain-capital-portfolio\", \"boostvc-portfolio\", \"cms-holdings-portfolio\", \"dcg-portfolio\", \"dragonfly-capital-portfolio\", \"electric-capital-portfolio\", \"fabric-ventures-portfolio\", \"framework-ventures-portfolio\", \"hashkey-capital-portfolio\", \"kenetic-capital-portfolio\", \"huobi-capital-portfolio\", \"alameda-research-portfolio\", \"a16z-portfolio\", \"1confirmation-portfolio\", \"winklevoss-capital-portfolio\", \"usv-portfolio\", \"placeholder-ventures-portfolio\", \"pantera-capital-portfolio\", \"multicoin-capital-portfolio\", \"paradigm-portfolio\", \"injective-ecosystem\", \"bnb-chain\"],
                        \"max_supply\": null,
                        \"circulating_supply\": 120601285.3115,
                        \"total_supply\": 120601285.3115,
                        \"platform\": null,
                        \"cmc_rank\": 2,
                        \"self_reported_circulating_supply\": null,
                        \"self_reported_market_cap\": null,
                        \"last_updated\": \"2022-05-01T10:21:00.000Z\"
                    }, {
                        \"id\": 825,
                        \"name\": \"Tether\",
                        \"symbol\": \"USDT\",
                        \"slug\": \"tether\",
                        \"num_market_pairs\": 32644,
                        \"date_added\": \"2015-02-25T00:00:00.000Z\",
                        \"tags\": [\"payments\", \"stablecoin\", \"asset-backed-stablecoin\", \"avalanche-ecosystem\", \"solana-ecosystem\", \"arbitrum-ecosytem\", \"moonriver-ecosystem\", \"injective-ecosystem\", \"bnb-chain\", \"usd-stablecoin\"],
                        \"max_supply\": null,
                        \"circulating_supply\": 83152877108.0891,
                        \"total_supply\": 85713951815.52061,
                        \"platform\": {
                            \"id\": 1027,
                            \"name\": \"Ethereum\",
                            \"symbol\": \"ETH\",
                            \"slug\": \"ethereum\",
                            \"token_address\": \"0xdac17f958d2ee523a2206206994597c13d831ec7\"
                        },
                        \"cmc_rank\": 3,
                        \"self_reported_circulating_supply\": null,
                        \"self_reported_market_cap\": null,
                        \"last_updated\": \"2022-05-01T10:21:00.000Z\"
                    }, {
                        \"id\": 1839,
                        \"name\": \"BNB\",
                        \"symbol\": \"BNB\",
                        \"slug\": \"bnb\",
                        \"num_market_pairs\": 822,
                        \"date_added\": \"2017-07-25T00:00:00.000Z\",
                        \"tags\": [\"marketplace\", \"centralized-exchange\", \"payments\", \"smart-contracts\", \"alameda-research-portfolio\", \"multicoin-capital-portfolio\", \"moonriver-ecosystem\", \"bnb-chain\"],
                        \"max_supply\": 165116760,
                        \"circulating_supply\": 163276974.63,
                        \"total_supply\": 163276974.63,
                        \"platform\": null,
                        \"cmc_rank\": 4,
                        \"self_reported_circulating_supply\": null,
                        \"self_reported_market_cap\": null,
                        \"last_updated\": \"2022-05-01T10:21:00.000Z\"
                    }, {
                        \"id\": 3408,
                        \"name\": \"USD Coin\",
                        \"symbol\": \"USDC\",
                        \"slug\": \"usd-coin\",
                        \"num_market_pairs\": 3853,
                        \"date_added\": \"2018-10-08T00:00:00.000Z\",
                        \"tags\": [\"medium-of-exchange\", \"stablecoin\", \"asset-backed-stablecoin\", \"fantom-ecosystem\", \"arbitrum-ecosytem\", \"moonriver-ecosystem\", \"bnb-chain\", \"usd-stablecoin\"],
                        \"max_supply\": null,
                        \"circulating_supply\": 49257055837.16886,
                        \"total_supply\": 49257055837.16886,
                        \"platform\": {
                            \"id\": 1027,
                            \"name\": \"Ethereum\",
                            \"symbol\": \"ETH\",
                            \"slug\": \"ethereum\",
                            \"token_address\": \"0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48\"
                        },
                        \"cmc_rank\": 5,
                        \"self_reported_circulating_supply\": null,
                        \"self_reported_market_cap\": null,
                        \"last_updated\": \"2022-05-01T10:21:00.000Z\"
                    }, {
                        \"id\": 5426,
                        \"name\": \"Solana\",
                        \"symbol\": \"SOL\",
                        \"slug\": \"solana\",
                        \"num_market_pairs\": 302,
                        \"date_added\": \"2020-04-10T00:00:00.000Z\",
                        \"tags\": [\"pos\", \"platform\", \"solana-ecosystem\", \"cms-holdings-portfolio\", \"kenetic-capital-portfolio\", \"alameda-research-portfolio\", \"multicoin-capital-portfolio\", \"okex-blockdream-ventures-portfolio\"],
                        \"max_supply\": null,
                        \"circulating_supply\": 334403220.0544914,
                        \"total_supply\": 511616946.142289,
                        \"platform\": null,
                        \"cmc_rank\": 6,
                        \"self_reported_circulating_supply\": null,
                        \"self_reported_market_cap\": null,
                        \"last_updated\": \"2022-05-01T10:21:00.000Z\"
                    }, {
                        \"id\": 52,
                        \"name\": \"XRP\",
                        \"symbol\": \"XRP\",
                        \"slug\": \"xrp\",
                        \"num_market_pairs\": 714,
                        \"date_added\": \"2013-08-04T00:00:00.000Z\",
                        \"tags\": [\"medium-of-exchange\", \"enterprise-solutions\", \"binance-chain\", \"arrington-xrp-capital-portfolio\", \"galaxy-digital-portfolio\", \"a16z-portfolio\", \"pantera-capital-portfolio\"],
                        \"max_supply\": 100000000000,
                        \"circulating_supply\": 48105234849,
                        \"total_supply\": 99989594553,
                        \"platform\": null,
                        \"cmc_rank\": 7,
                        \"self_reported_circulating_supply\": null,
                        \"self_reported_market_cap\": null,
                        \"last_updated\": \"2022-05-01T10:22:00.000Z\"
                    }, {
                        \"id\": 4172,
                        \"name\": \"Terra\",
                        \"symbol\": \"LUNA\",
                        \"slug\": \"terra-luna\",
                        \"num_market_pairs\": 232,
                        \"date_added\": \"2019-07-26T00:00:00.000Z\",
                        \"tags\": [\"cosmos-ecosystem\", \"store-of-value\", \"defi\", \"payments\", \"coinbase-ventures-portfolio\", \"binance-labs-portfolio\", \"solana-ecosystem\", \"arrington-xrp-capital-portfolio\", \"hashkey-capital-portfolio\", \"kenetic-capital-portfolio\", \"huobi-capital-portfolio\", \"pantera-capital-portfolio\", \"terra-ecosystem\"],
                        \"max_supply\": null,
                        \"circulating_supply\": 345232041.7401862,
                        \"total_supply\": 728248457.755222,
                        \"platform\": null,
                        \"cmc_rank\": 8,
                        \"self_reported_circulating_supply\": null,
                        \"self_reported_market_cap\": null,
                        \"last_updated\": \"2022-05-01T10:22:00.000Z\"
                    }
            ]
            }""";
    }

    public String getMetaData(String key, int id) {
        return """
            {\"status\":{\"timestamp\":\"2022-05-01T10:23:15.373Z\",\"error_code\":0,\"error_message\":null,\"elapsed\":20,\"credit_count\":1,\"notice\":null},
            \"data\":{\"1\":{\"id\":1,\"name\":\"Bitcoin\",\"symbol\":\"BTC\",\"category\":\"coin\",\"description\":\"Bitcoin (BTC) is a cryptocurrency . Users are able to generate BTC through the process of mining. Bitcoin has a current supply of 19,027,400. The last known price of Bitcoin is 38,012.14022782 USD and is down -1.56 over the last 24 hours. It is currently trading on 9379 active market(s) with $26,985,504,678.42 traded over the last 24 hours. More information can be found at https://bitcoin.org/.\",\"slug\":\"bitcoin\",\"logo\":\"https://s2.coinmarketcap.com/static/img/coins/64x64/1.png\",\"subreddit\":\"bitcoin\",\"notice\":\"\",\"tags\":[\"mineable\",\"pow\",\"sha-256\",\"store-of-value\",\"state-channel\",\"coinbase-ventures-portfolio\",\"three-arrows-capital-portfolio\",\"polychain-capital-portfolio\",\"binance-labs-portfolio\",\"blockchain-capital-portfolio\",\"boostvc-portfolio\",\"cms-holdings-portfolio\",\"dcg-portfolio\",\"dragonfly-capital-portfolio\",\"electric-capital-portfolio\",\"fabric-ventures-portfolio\",\"framework-ventures-portfolio\",\"galaxy-digital-portfolio\",\"huobi-capital-portfolio\",\"alameda-research-portfolio\",\"a16z-portfolio\",\"1confirmation-portfolio\",\"winklevoss-capital-portfolio\",\"usv-portfolio\",\"placeholder-ventures-portfolio\",\"pantera-capital-portfolio\",\"multicoin-capital-portfolio\",\"paradigm-portfolio\"],\"tag-names\":[\"Mineable\",\"PoW\",\"SHA-256\",\"Store Of Value\",\"State Channel\",\"Coinbase Ventures Portfolio\",\"Three Arrows Capital Portfolio\",\"Polychain Capital Portfolio\",\"Binance Labs Portfolio\",\"Blockchain Capital Portfolio\",\"BoostVC Portfolio\",\"CMS Holdings Portfolio\",\"DCG Portfolio\",\"DragonFly Capital Portfolio\",\"Electric Capital Portfolio\",\"Fabric Ventures Portfolio\",\"Framework Ventures Portfolio\",\"Galaxy Digital Portfolio\",\"Huobi Capital Portfolio\",\"Alameda Research Portfolio\",\"a16z Portfolio\",\"1Confirmation Portfolio\",\"Winklevoss Capital Portfolio\",\"USV Portfolio\",\"Placeholder Ventures Portfolio\",\"Pantera Capital Portfolio\",\"Multicoin Capital Portfolio\",\"Paradigm Portfolio\"],\"tag-groups\":[\"OTHERS\",\"ALGORITHM\",\"ALGORITHM\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\"],\"urls\":{\"website\":[\"https://bitcoin.org/\"],\"twitter\":[],\"message_board\":[\"https://bitcointalk.org\"],\"chat\":[],\"facebook\":[],\"explorer\":[\"https://blockchain.coinmarketcap.com/chain/bitcoin\",\"https://blockchain.info/\",\"https://live.blockcypher.com/btc/\",\"https://blockchair.com/bitcoin\",\"https://explorer.viabtc.com/btc\"],\"reddit\":[\"https://reddit.com/r/bitcoin\"],\"technical_doc\":[\"https://bitcoin.org/bitcoin.pdf\"],\"source_code\":[\"https://github.com/bitcoin/bitcoin\"],\"announcement\":[]},\"platform\":null,\"date_added\":\"2013-04-28T00:00:00.000Z\",\"twitter_username\":\"\",\"is_hidden\":0,\"date_launched\":\"1969-04-20\",\"contract_address\":[],\"self_reported_circulating_supply\":null,\"self_reported_tags\":null,\"self_reported_market_cap\":null}}}
        """;
    }

    public String getConversion(String key, int from, int to, double amount) {
        
        try {
            Thread.sleep(900);  
        } catch (Exception e) {}
        return """
            {\"status\":{\"timestamp\":\"2022-05-01T10:23:42.178Z\",\"error_code\":0,\"error_message\":null,\"elapsed\":63,\"credit_count\":1,\"notice\":null},\"data\":{\"id\":1,\"symbol\":\"BTC\",\"name\":\"Bitcoin\",\"amount\":12,\"last_updated\":\"2022-05-01T10:23:00.000Z\",\"quote\":{\"2010\":{\"price\":589568.9593595711,\"last_updated\":\"2022-05-01T10:23:00.000Z\"}}}}
        """;
    }
}
