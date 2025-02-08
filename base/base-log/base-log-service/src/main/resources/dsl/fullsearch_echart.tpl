{
  "query": {
    "bool": {
		"must_not": [
        #if($notmachine)
		{
          "match": {
            "machine":"${notmachine}"
          }
        },
		#end
		#if($notsyscode)
		{
          "match": {
            "syscode":"${notsyscode}"
          }
        },
		#end
		#if($notappcode)
		{
          "match": {
            "appcode":"${notappcode}"
          }
        },
		#end
		#if($notfilename)
		{
          "match": {
            "filename":"${notfilename}"
          }
        },
		#end
		#if($notnodecode)
		{
          "match": {
            "nodecode":"${notnodecode}"
          }
        },
		#end
		{
          "match": {
            "1":"1"
                      }
        }
      ],
      "must": [
		#if($stauth)
		{
          "terms": {
            "nodecode": ${stauth}
          }
        },
		#end
		#if($appauth)
		{
          "terms": {
            "appcode": ${appauth}
          }
        },
		#end
		#if($sysauth)
		{
          "terms": {
            "syscode": ${sysauth}
          }
        },
		#end
		#if($logdata)
			{
				"query_string": {
					"default_field": "logdata",
					"query": "${logdata}"
				}
			},
		#end
		#if($machine)
        {
          "match": {
            "machine":"${machine}"
          }
        },
		#end
		#if($syscode)
        {
          "match": {
            "syscode":"${syscode}"
          }
        },
		#end
		#if($appcode)
        {
          "match": {
            "appcode":"${appcode}"
          }
        },
		#end
		#if($filename)
        {
          "match": {
            "filename":"${filename}"
          }
        },
		#end
		#if($nodecode)
        {
          "match": {
            "nodecode":"${nodecode}"
          }
        },
		#end
		{
          "range": {
            "gettime": {
              "gte": "${mintime}",
              "lte": "${maxtime}",
              "format": "yyyy-MM-dd HH:mm:ss"
            }
          }
        }
      ]
    }
  },
  "aggs": {
    "numcont": {
      "date_histogram": {
        "field": "gettime",
        "interval": "${interval}",
        "min_doc_count": 0,
		"format": "yyyy-MM-dd HH:mm:ss",
        "extended_bounds": {
          "min": "${mintime}",
          "max": "${maxtime}"
        }
      }
    },
    "all_interests": {
      "terms": {
        "field": "syscode.keyword",
        "size": 10000
      }
    }
  },
  "size": 0
}