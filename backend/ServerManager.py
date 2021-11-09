from DB.database import DatabaseManager
import string
import random
import time

def get_random_string(length):
    letters = string.ascii_lowercase
    return ''.join(random.choice(letters) for i in range(length))

class ServerManager:
    
    def __init__(self, databasemanager : DatabaseManager) -> None:
        self.Tokens = {} #Tokens will give User info, last access
        self.DatabaseManager = databasemanager

    def ClearOldTokens(self):
        #todo implement 
        pass

    def RegisterToken(self, user_dict):
        self.ClearOldTokens()

        newtoken = get_random_string(16)

        while newtoken in self.Tokens:
            newtoken = get_random_string(16)

        d = {
            'userid': user_dict["id"]
        }

        self.Tokens[newtoken] = d

        self.UpdateToken(newtoken)


    def UpdateToken(self, token):
        if token in self.Tokens:
            self.Tokens[token]["LastAccessTime"]= time.time()


    def GetUserId(self, token):
        return self.Tokens[token]["userid"]