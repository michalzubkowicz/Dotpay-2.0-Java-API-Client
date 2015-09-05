# Dotpay-2.0-Java-API-Client
Prosta klasa do obsługi do weryfikacji płatności przesłanych przez Dotpay

# Użycie:
Dotpay d = new Dotpay(mp); //mp to zmapowane wszsytkie pola zapytania GET lub POST
d.checkSignature(pin); //sprawdza poprawność signatury płatności
d.checkIP(ip); Sprawdza czy podane IP to poprawne IP bramki dotpay
