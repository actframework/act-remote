# Payload encoding

This chapter describes the possible HTTP request payload encoding approach 
for complex data structure

## Terms

### Simple data type

The data that could be encoded into (and decoded from) a String without 
customisation work. Data of the following types are simple data:

* primitive types and corresponding wrapper types
* `java.lang.String`
* `java.math.BigDecimal`
* `java.math.BigInteger`
* Enum types
* Date time types (Certain format must be agreed between the two ends)

Simple data type does not require special enode scheme specfied in this 
document.

### Complex data type

The data that could not be encoded into (and decoded from) a single string. 
Instead the data needs to be encoded into request using multiple parameters. 
Examples:

* List/Set/Array of simple data
* Map (simple data map to simple data)
* POJO types

## List of simple data

There are multiple way of encoding list of simple data into a request. 

**Note** Here "list" refers to linear data structure including

1. `java.util.List`
2. `java.util.Set`
3. Array type

Example data:

```java
int[] na = {1, 2, 3};
```

### 1. NAME_ONLY

Name only

Query string:

```
?na=1&na=2&na=3
```

### 2. NAME_BRACKET

Name with brackets

Query string:

```
?na[]=1&na[]=2&na[]=3
```

### 3. NAME_BRACKET_INDEX

Name with indexes in bracket

Query string:

```
?na[0]=1&na[1]=2&na[2]=3
```

**Note** the index might not be ordered and even not in a sequence, e.g:

```
?na[3]=2&na[5]=1&na[9]=0
```

### 4. NAME_DOT_INDEX

Name with indexes separated by dot

Query string:

```
?na.0=1&na.1=2&na.3=3
```

**Note** the index might not be ordered and even not in a sequence. e.g:

```
?na.9=1&na.2=3&na.4=0
```

### 5. JSON

encode data with JSON string

```json
{
"na": [1, 2, 3]
}
```

## Map of simple data

There are multiple way of encoding map of simple data into request.

Example data:

```
Map<String, Integer> map = C.newMap("One", 1, "Two", 2);
```

### 1. NAME_ONLY

Name only - this encoding will be exactly the same with `NAME_DOT_INDEX` 
when encoding Map of simple data

### 2. NAME_BRACKET

Name with empty bracket - this encoding will be exactly the same with 
`NAME_RACKET_INDEX` when encoding Map of simple data

### 3. NAME_BRACKET_INDEX

Name with brackets and indexes

Query string:

```
?map[One]=1&map[Two]=2
```

### 4. NAME_DOT_INDEX

Name with indexes separated by dot

Query string:

```
?map.One=1&map.Two=2
```

### 5. JSON

Encode map of simple data in JSON string

```json
{
"map": {
    "One": 1,
    "Two": 2
}
}
```

## POJO data

Example classes:

```java
public class Address {
	private String street;
	private String suburb;
	private String state;
	private String postCode;
	// following getters and settings
}
```

Example data with embedded complex structures

```java
public class Contact {
    public enum Type {HOME, WORK, POST}
	private String mobile;
	private List<String> otherPhones;
	private Map<Type, Address> addresses;
	// following getters and setters
}
```

Example data:

```yaml
Address(addr_peter_home):
	street: 1/4 David Cl
	suburb: Breakfast point
	state: NSW
	postCode: 2323
	
Address(addr_peter_office):
	street #1 Bligh St
	suburb: City
	state: NSW
	postCode: 2000
	
Contact(peter):
    name: peter
	mobile: 0403123123
	otherPhones:
		- 02 89901123
	addresses:
		HOME: ref:addr_peter_home
		WORK: ref:addr_peter_office
```

### 1. NANE_ONLY

Name only 
 
When there is no linear data types (array, list, set) in the POJO class, 
the `NAME_ONLY` will perform exactly the same with `NAME_DOT_INDEX`. When 
linear data type is encountered, the "name only" notation will be used
to encode the element inside

#### Form data for `addr_peter_home`

See the corresponding section of `NAME_DOT_INDEX`

#### Form data for `peter`

suppose param name is `ctct`

| param name | param value |
| ---------- | ----------- |
| `ctct.name` | peter |
| `ctct.mobile` | 0403123123 |
| `ctct.otherPhones` | 02 89901123 |
| `ctct.addresses.HOME.street` | 1/4 David Cl |
| `ctct.addresses.HOME.suburb` | Breakfast point |
| `ctct.addresses.HOME.state` | NSW |
| `ctct.addresses.HOME.postCode` | 2323 |
| `ctct.addresses.WORK.street` | #1 Bligh St |
| `ctct.addresses.WORK.suburb` | City |
| `ctct.addresses.WORK.state` | NSW |
| `ctct.addresses.WORK.postCode` | 2000 |


### 2. NAME_BRACKET

Name with empty bracket

When there is no linear data types (array, list, set) in the POJO class, 
the `NAME_BRACKET` will perform exactly the same with `NAME_BRACKET_INDEX`. 
When linear data type is encountered, the "empty bracket" notation will 
be used to encode the elements inside

#### Form data for `addr_peter_home` 

See the corresponding section of `NAME_BRACKET_INDEX`

#### Form data for `peter`

suppose param name is `ctct`

| param name | param value |
| ---------- | ----------- |
| `ctct[name]` | peter |
| `ctct[mobile]` | 0403123123 |
| `ctct[otherPhones][]` | 02 89901123 |
| `ctct[addresses][HOME][street]` | 1/4 David Cl |
| `ctct[addresses][HOME][suburb]` | Breakfast point |
| `ctct[addresses][HOME][state]` | NSW |
| `ctct[addresses][HOME][postCode]` | 2323 |
| `ctct[addresses][WORK][street]` | #1 Bligh St |
| `ctct[addresses][WORK][suburb]` | City |
| `ctct[addresses][WORK][state]` | NSW |
| `ctct[addresses][WORK][postCode]` | 2000 |
 

### 3. NAME_BRACKET_INDEX

Name with indexes in brackets

#### Form data for `addr_peter_home` 

suppose param name is `addr`

| param name | param value |
| ---------- | ----------- |
| `addr[street]` | 1/4 David Cl |
| `addr[suburb]` | Breakfast point |
| `addr[state]` | NSW |
| `addr[postCode]` | 2323|

#### Form data for `peter` 

suppose param name is `ctct`

| param name | param value |
| ---------- | ----------- |
| `ctct[name]` | peter |
| `ctct[mobile]` | 0403123123 |
| `ctct[otherPhones][0]` | 02 89901123 |
| `ctct[addresses][HOME][street]` | 1/4 David Cl |
| `ctct[addresses][HOME][suburb]` | Breakfast point |
| `ctct[addresses][HOME][state]` | NSW |
| `ctct[addresses][HOME][postCode`] | 2323 |
| `ctct[addresses][WORK][street]` | #1 Bligh St |
| `ctct[addresses][WORK][suburb]` | City |
| `ctct[addresses][WORK][state]` | NSW |
| `ctct[addresses][WORK][postCode]` | 2000 |

**Note** the only difference with the data encoded by `NAME_BRACKET` is 
the param name of the `otherPhones` data, which is a linear data type in 
`Contact` class. To highlight the difference, the data encoded by the two 
encoding scheme is listed below:

| Encoding | Param name |
| -------- | ---------- |
| `NAME_BRACKET` | `ctct[otherPhones][]` |
| `NAME_BACKET_INDEX` | `ctct[otherPhones][0]` |

### 4. NAME_DOT_INDEX

Name with indexes separated by dot

#### Form data for `addr_peter_home` 

suppose param name is `addr`

| param name | param value |
| ---------- | ----------- |
| `addr.street` | 1/4 David Cl |
| `addr.suburb` | Breakfast point |
| `addr.state` | NSW |
| `addr.postCode` | 2323|

#### Form data for `peter` 

suppose param name is `ctct`

| param name | param value |
| ---------- | ----------- |
| `ctct.name` | peter |
| `ctct.mobile` | 0403123123 |
| `ctct.otherPhones.0` | 02 89901123 |
| `ctct.addresses.HOME.street` | 1/4 David Cl |
| `ctct.addresses.HOME.suburb` | Breakfast point |
| `ctct.addresses.HOME.state` | NSW |
| `ctct.addresses.HOME.postCode` | 2323 |
| `ctct.addresses.WORK.street` | #1 Bligh St |
| `ctct.addresses.WORK.suburb` | City |
| `ctct.addresses.WORK.state` | NSW |
| `ctct.addresses.WORK.postCode` | 2000 |

**Note** the difference between `NAME_DOT_INDEX` and `NAME_ONLY` on encoding 
`peter` is the parameter name of `otherPhones` data,  which is a linear 
data type in `Contact` class. To highlight the difference, the data encoded 
by the two encoding scheme is listed below:

| Encoding | Param name |
| -------- | ---------- |
| `NAME_ONLY` | `ctct.otherPhones` |
| `NAME_DOT_INDEX` | `ctct.otherPhones.0` |

### 5. JSON

Encode POJO data in JSON string

#### JSON body for `addr_peter_home` 

```json
{
    "addr": {
        "street": "1/4 David Cl",
        "suburb": "Breakfast point",
        "state": "NSW",
        "postCode": 2323
    }
}
```

#### JSON body for `peter`

```json
{
    "ctct": {
        "name": "peter",
        "mobile: "0403123123",
        "otherPhones": ["02 89901123"],
        "addresses": {
            "HOME": {
                "street": "1/4 David Cl",
                "suburb": "Breakfast point",
                "state": "NSW",
                "postCode": "2323"
            },
            "WORK": {
                "street": "#1 Bligh St",
                "suburb": "City",
                "state": "NSW",
                "postCode": "2000"
            }
        }
    }
}
```

