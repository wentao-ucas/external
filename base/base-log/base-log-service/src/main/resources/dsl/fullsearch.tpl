{
  "query": {
    "bool": {
		"must_not": [
        #if($notmachine)
		{
          "wildcard": {
            "machine":"${notmachine}"
          }
        },
		#end
		#if($notsyscode)
		{
          "wildcard": {
            "syscode":"${notsyscode}"
          }
        },
		#end
		#if($notappcode)
		{
          "wildcard": {
            "appcode":"${notappcode}"
          }
        },
		#end
		#if($notfilename)
		{
          "wildcard": {
            "filename":"${notfilename}"
          }
        },
		#end
		#if($notnodecode)
		{
          "wildcard": {
            "nodecode":"${notnodecode}"
          }
        },
		#end
		{
          "wildcard": {
            "1": {
              "value": "1"
            }
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
          "wildcard": {
            "machine":"${machine}"
          }
        },
		#end
		#if($syscode)
		{
          "wildcard": {
            "syscode":"${syscode}"
          }
        },
		#end
		#if($appcode)
		{
          "wildcard": {
            "appcode":"${appcode}"
          }
        },
		#end
		#if($filename)
		{
          "wildcard": {
            "filename":"${filename}"
          }
        },
		#end
		#if($nodecode)
		{
          "wildcard": {
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
  "sort": [
    {
      "gettime": "desc"
    }
  ],
  #if($logdata)
  "highlight": {
    "fields": {
      "logdata": {
        "pre_tags": "<em>",
        "post_tags": "</em>"
      }
    }
  },
  #end
  "from": ${from},
  "size": ${size}
}