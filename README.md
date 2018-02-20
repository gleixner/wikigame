# wikigame
Playing the wikigame

Rules:
Pick a wiki page as start, and pick a second as the end (tradition dictates you choose Kevin Bacon).
Using only the links in the article itself, get from the first page to the second in as few clicks as possible.

For example:
Start: Fenestrelle Fort
End: Kevin Bacon
Solution: https://en.wikipedia.org/wiki/Fenestrelle_Fort --> https://en.wikipedia.org/wiki/Turin --> https://en.wikipedia.org/wiki/Michael_Caine --> /wiki/Kevin_Bacon

In reality, this is a simple multi-threaded webcrawler built using Jsoup and a depth first traversal of Wikipedia to find the shortest distance two nodes in an unweighted graph.
