import re
EmailRegex = r'\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}\b'


def CheckEmail(email):

    aaa = re.fullmatch(EmailRegex, email)
    if aaa is None:
        return False, "Email is not valid!"
    return True, None

def CheckLocation(loc):
    if loc is None:
        return False, "loc can't be null!"

    if not "locname" in loc:
        return False, "locname is not defined in location object!"
    if not "longitude" in loc:
        return False, "longitude is not defined in location object!"
    if not "latitude" in loc:
        return False, "latitude is not defined in location object!"
    
    if not isinstance(loc["locname"], str):
        return False, "locname is not a str in location object!"
    if not isinstance(loc["longitude"], float):
        return False, "longitude is not a float in location object!"
    if not isinstance(loc["latitude"], float):
        return False, "latitude is not a float in location object!"

    if len(loc) != 3:
        return False, "extra params in location object!"

    loc["@type"] = "Location.Object"
    return True, None


def CheckPassword(password):
 
    if not (len(password) >= 8 and len(password) <= 16):
        return False, "Password Length must be >= 8, <= 16!"

    if sum(1 for c in password if c.isupper()) == 0:
        return False, "Password must contain at least 1 upper case letter!"

    return True, None