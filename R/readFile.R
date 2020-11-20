#Each line in file Mx is a treatment of Agility Method (A1, A2 and A3) applied to a scenario
scenarios <- c(1,2) #Number of Scenarios to be interact. It is the same number of folders in Eclipse Workspace
NoS <- length(scenarios)
path <- '/Users/junier/git/PIBIC-C2/'
metrics <- c(1,2,3,4)
means <- matrix(nrow=NoS, ncol=3, dimnames = list(c(seq(NoS)), c("A1","A2","A3")))
sd <- matrix(nrow=NoS, ncol=3, dimnames = list(c(seq(NoS)), c("A1","A2","A3")))
#specify_decimal <- function(x, k) trimws(format(round(x, k), nsmall=k)) #function used to format the numbers in results
for(i in scenarios){
  for(j in metrics){
    file <- paste0(path,i,'/','M',j)
    data <- read.csv(file = file, sep=';', header = FALSE)
    for(A in c(1,2,3)){ #get each treatment for Agility Method
      d = ncol(data[A,]) - 1 #to remove the last semicolon
      #mat <- as.matrix(data[j,1:d])
      means[i,A] <- round(rowMeans(data[A,], na.rm = TRUE), 1)
      sd[i,A] <- round(sd(data[A,], na.rm = TRUE), 2)
      #print(specify_decimal(rowMeans(data[2,], na.rm = TRUE), 1))
      #print(specify_decimal(sd(mat),2))
    }
    print(paste0("METRIC => ",j))
    #means <- as.numeric(means)
    #sd <- as.numeric(sd)
    
  }
}
output <- paste0(path,'mean',j)
write.table(means,file=output)
output <- paste0(path,'sd',j)
write.table(sd,file=output)
print(means)
print(sd)
