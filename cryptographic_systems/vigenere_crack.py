import enchant

dic = enchant.Dict("en_US")
encrypted = "TRLSHAXRNSVKIENUFMEGRVDANEELHOFNSLUGIEFZVATAAGCIYAGIFADWUDHFYIFPOWVSPUMBKOTUOBYYNQWZYEEHBFCYCRZUKIPDZFFOYDBPZTPRBRVRFRBFYESLSXUAALBFIIAVWORLYBAAIAYGWYVNFLCZKHRVBANDRQFQMEYDHUFNFPCFZVNWSMIENVGQJSZHBFFFGKSBFLVWWORLNQRYFRNODAJIGLCZZNTRTOIYCWCSIACKMFYELOSMUOAHHARSXLTALRVQONZLVWMFFESISOKIIHZKRDQUSEJMNVGELRIHWXCAAFSOFNFWWFLTRVORRIYXFQFFBXFRZEYGWNVLVHJQKHNWWFUORVWORLYICDRCBPAGEIGBKUUERITAITGRRQMEYRDYFRRHTRVCGLJQDENQGFFRRVWEKMNVGELRIHWXCAAFSUGLRDRRFRNUSUEVRQHUFNBICGIDVVQUGLVQODPCHOHGIEGROFKEAGBAKOAOMFFPHCNXVSNQRYRTUEIFRLFRHAKHRVCOZEGDZUDPYLQMKIBQGAWOHUKAIK"
ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

# vigenere decrypt
def decrypt_vigenere(ciphertext, key):
    key_length = len(key)
    key_as_int = [ord(i) for i in key]
    ciphertext_int = [ord(i) for i in ciphertext]
    plaintext = ''
    for i in range(len(ciphertext_int)):
        value = (ciphertext_int[i] - key_as_int[i % key_length]) % 26
        plaintext += chr(value + 65)
    return plaintext

# get factors larger than 1 from number
def get_factors(x):
    factors = []
    for i in range(2, x + 1):
       if x % i == 0:
            factors.append(i)
    return factors

# caesar decrypt
def decrypt_caesar(key, message):
    message = message.upper()
    result = ""
    for letter in message:
        if letter in ALPHABET:
            letter_index = (ALPHABET.find(letter) - key) % len(ALPHABET)
            result = result + ALPHABET[letter_index]
        else:
            result = result + letter

    return result

# get permutations from 2d array given as a parameter
def product(*seqs):
    if not seqs:
        return [[]]
    else:
        return [[x] + p for x in seqs[0] for p in product(*seqs[1:])]

# function for frequency analysis metric
def eng_freq_score(string):
    eng_freq = "ETAOINSHRDLCUMWFGYPBVKJXQZ"
    eng_freq_list = [c for c in eng_freq]
    counts = {}
    for char in ALPHABET:
        counts[char] = 0
    
    for char in string:
        counts[char] += 1
    
    word_count_order = sorted(counts.items(), key=lambda x:x[1], reverse=True)
    match_score = 0
    value_dic = {}
    for c in word_count_order:
        if c[1] not in value_dic:
            value_dic[c[1]] = [c[0]]
        else:
            value_dic[c[1]].append(c[0]) 
    values = list(value_dic.values())
    i = 0
    j = 0
    while eng_freq_list:
        try:
            curr_val = values[i][j] # if j is out of range we will go to except block 
            curr_l = values[i]
            j += 1
        except:
            j = 0
            i += 1
            continue
        curr_freq = eng_freq_list.pop(0)
        for n in curr_l:
            if curr_freq == n:
                match_score += 1
    return match_score

def vigenere_subkeys(num, string):
    substrings = []
    str_len = len(string)
    for n in range(num):
        substrings.append("")
        for i in range(n, str_len, num):
            substrings[n] += string[i]
    
    subkeys = []
    for s in substrings:
        substring_eng_scores = {}
        for a in ALPHABET:
            decrypted = decrypt_caesar(ord(a)-65, s)
            score = eng_freq_score(decrypted)
            substring_eng_scores[a] = score
        subkeys.append([x[0] for x in sorted(substring_eng_scores.items(), reverse=True, key=lambda x:x[1]) if x[1] > 4])
    return subkeys

if __name__ == "__main__":
    # count three letter occurrences and their indexes
    counts = {}
    length = len(encrypted)
    for i in range(1, length):
        curr = f"{encrypted[i-2]}{encrypted[i-1]}{encrypted[i]}"
        if curr in counts.keys():
            counts[curr][0] += 1
            counts[curr][1].append(i)
        else:
            counts[curr] = [1, [i]]
    
    multiple_matches = [x for x in counts.items() if x[1][0] > 1]

    # calculate spaces between all the occurrences
    spaces_betw_matches = {}
    for match in multiple_matches:
        spaces_betw_matches[match[0]] = []
        for m in range(1, match[1][0]):
            spaces_betw_matches[match[0]].append(match[1][1][m] - match[1][1][m-1])

    # calculate the factors from the spaces between occurrences
    factor_occurrences = {}
    for space in spaces_betw_matches:
        for n in spaces_betw_matches[space]:
            factors = get_factors(n)
            for f in factors:
                f = str(f)
                if f not in factor_occurrences:
                    factor_occurrences[f] = 1
                else:
                    factor_occurrences[f] += 1

    #print(sorted(factor_occurrences.items(), reverse=True, key=lambda x:x[1]))

    # get factors for analysis
    nums_to_test = [int(x[0]) for x in factor_occurrences.items() if (x[1] > 40)]
    possible_keys = []

    for num in nums_to_test:
        lists = vigenere_subkeys(num, encrypted) # return subkeys for string
        perms = product(*lists) # brute force all possible permutations of subkeys 
        
        perms_len = len(perms) 
        print(perms_len)
        for i in range(perms_len):
            perms[i] = "".join(perms[i]) # make list into string for dictionary check
            # if word found in english lang dictionary, add it to list of possible keys
            if dic.check(perms[i]): 
                possible_keys.append(perms[i]) 

    print(possible_keys)

    # check all possible keys for validity
    for key in possible_keys:
        print(key, decrypt_vigenere(encrypted, key), "\n")
