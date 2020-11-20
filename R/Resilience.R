############################################################################
# By Junier Amorim
# Resilience -> Effectiveness recover capability for each Scenario
############################################################################

#################################
####         BOXPLOT         ####
#################################
dataset <- data.frame(matrix(ncol = 20, nrow = 500))
OUTLIERS <- FALSE
names(dataset) <- c("A1","A2","A1","A2","A1","A2","A1","A2","A1","A2","A1","A2","A1","A2","A1","A2","A1","A2","A1","A2")
scenarios <- c(1,2,3,4,5,6,7,8,9,10)  
NoS <- length(scenarios)
path <- '/Users/junier/git/PIBIC-C2/Results/'
process <- TRUE # Remove outliers (greater than 2*standard deviation)
j <- 4 # Effectiveness
file0_0 <- paste0(path,'1_R/','M',j)
data_base <- read.csv(file = file0_0, sep=';', header = FALSE)
#Process data_base
if(process){
  for(A in c(1,2)){ #get each treatment for Agility Method
    d = ncol(data_base[A,]) 
    mean <- round(rowMeans(data_base[A,], na.rm = TRUE), 1)
    sd <- round(sd(data_base[A,], na.rm = TRUE), 1)
    threshold_min <- mean - sd
    threshold_max <- mean + sd
    for(pos in seq(d)){
      if((data_base[A,pos] <= threshold_min)||(data_base[A,pos] >= threshold_max)){
        data_base[A,pos] <- mean;
        }
    }
  }
}

for(i in scenarios){
  file <- paste0(path,i,'/','M',j)
  data <- read.csv(file = file, sep=';', header = FALSE)
  for(A in c(1,2)){ #get each treatment for Agility Method
    d = ncol(data[A,]) 
    
    data[A,]=(data[A,]*100)/data_base[A,]
    data[A,] <- round(data[A,], 1)
    
    if(process){
      mean <- round(rowMeans(data[A,], na.rm = TRUE), 1)
      sd <- round(sd(data[A,], na.rm = TRUE), 1)
      threshold_min <- mean - sd
      threshold_max <- mean + sd
      for(pos in seq(d)){
       if((data[A,pos] <= threshold_min)||(data[A,pos] >= threshold_max)){
          data[A,pos] <- mean;
        }
      }
    }
    
    
   
      
    for(pos in seq(d)){
      dataset[pos,2*(i-1)+A] <- data[A,pos]
    }
  }
}
  
  name<- paste0("Boxplot_M",j+1,".png")
  png(name)
  par(cex.lab=1.3) # is for y-axis
  par(cex.axis=1.3) # is for x-axis
  par(cex.main=1.5)
  g <- boxplot(dataset, xaxt="n", ylim = c(0, max(dataset[])), col = c("white","darkgrey"), outline=OUTLIERS, ylab ="Resilience", xlab ="Scenarios",main="Resilience(M5)")
  axis(1,at=seq(1.5,20,2), labels=c(1,2,3,4,5,6,7,8,9,10))+abline(v=seq(0.5,21,2),lty=3,col="blue")
  legend("bottomleft",legend=c("A1", "A2"),
         col=c("A1", "A2"),fill=c("white","darkgrey"),
         title="Action Methods", text.font=4, inset = .15,horiz=TRUE, cex=1)
  
  #show(g)
  dev.off()
  


  #################################
  ####           LINE          ####
  #################################

library(ggplot2)
library(ggpubr)

NoS <- 10
path <- '/Users/junier/git/PIBIC-C2/Results/'
scenarios <- c(1,2,3,4,5,6,7,8,9,10)
#scenarios <- c(1)
metrics <- c(1,2,3,5) 
  i <- 4 
  file0_0 <- paste0(path,'mean_M',i,'_Base')
  file0_1 <- paste0(path,'sd_M',i,'_Base')
  file1 <- paste0(path,'mean_M',i)
  file2 <- paste0(path,'sd_M',i)
  mean_data <- read.table(file = file1, header = TRUE)
  sd_data <- read.table(file = file2, header = TRUE)
  #The base results is obtained from a running without context changes to see the best result that could be obtained by the system
  mean_base <- read.table(file = file0_0, header = TRUE)
  sd_base <- read.table(file = file0_1, header = TRUE)
  
  for(i in scenarios){
    sd_data[i,]=(((mean_data[i,]+sd_data[i,])*100)/(mean_base[1,]+sd_base[1,]))
    mean_data[i,]=(mean_data[i,]*100)/mean_base[1,]
    sd_data[i,]=sd_data[i,]-mean_data[i,]
    
    mean_data[i,] <- round(mean_data[i,], 1)
    sd_data[i,] <- round(abs(sd_data[i,]), 2)
  }
  
  output <- paste0(path,'mean_M5')
  write.table(mean_data,file=output)
  output <- paste0(path,'sd_M5')
  write.table(sd_data,file=output)
  
  A1 <- matrix(nrow=NoS, ncol=5, dimnames = list(c(seq(NoS)), c("sc","effec","effec_min","effec_max","type")))
  A2 <- matrix(nrow=NoS, ncol=5, dimnames = list(c(seq(NoS)), c("sc","effec","effec_min","effec_max","type")))
  A3 <- matrix(nrow=NoS, ncol=5, dimnames = list(c(seq(NoS)), c("sc","effec","effec_min","effec_max","type")))
  
for(i in scenarios){
   A1[i,] <- c(i, mean_data[i,1],mean_data[i,1]-sd_data[i,1],mean_data[i,1]+sd_data[i,1], "A1")
  #A1[2,] <- c(2, mean_data[2,1],mean_data[2,1]-sd_data[2,1],mean_data[2,1]+sd_data[2,1], "A1")
  #A1 <- data.frame(A1)
#A2 as A3 
  A2[i,] <- c(i, mean_data[i,2],mean_data[i,2]-sd_data[i,2],mean_data[i,2]+sd_data[i,2], "A2")
  #A2[2,] <- c(2, mean_data[2,2],mean_data[2,2]-sd_data[2,2],mean_data[2,2]+sd_data[2,2], "A2")
  #A2 <- data.frame(A2)
#  A3[i,] <- c(i, mean_data[i,3],mean_data[i,3]-sd_data[i,3],mean_data[i,3]+sd_data[i,3], "A3")
  #A3[2,] <- c(2, mean_data[2,3],mean_data[2,3]-sd_data[2,3],mean_data[2,3]+sd_data[2,3], "A3")
  
}
  
A1 <- data.frame(A1)
A2 <- data.frame(A2)
#A3 <- data.frame(A3)
#A <- rbind(A1,A2,A3)
A <- rbind(A1,A2)
A <- data.frame(A)
A[,1] <- as.numeric(A[,1])
A[,2] <- as.numeric(A[,2])
A[,3] <- as.numeric(A[,3])
A[,4] <- as.numeric(A[,4])

# Make the plot
graph <- ggplot(data=A, aes(x=sc, y=effec, ymin=effec_min, ymax=effec_max, fill = type, linetype=type)) + 
  geom_line() +
  #guides(fill=FALSE) + 
  geom_ribbon(alpha=0.2) +
  #scale_x_log10() + 
  scale_x_discrete(limits=as.factor(scenarios)) +
  scale_y_log10() + 
  #xlab("Scenario") + 
  labs(fill="Action \n Method",linetype="Action \n Method", x="Scenario", y="Resilience (M5)") +
  #ylab("Resilience (M4)") #+
  #scale_linetype_manual("Action Method",values=c("A1"=1,"A2"=2))+scale_fill_brewer(palette="Dark2")
  theme(axis.text.x = element_text(color = "grey20", size = 13, angle = 0, hjust = .5, vjust = .5, face = "plain"),
      axis.text.y = element_text(color = "grey20", size = 13, angle = 0, hjust = 1, vjust = 0, face = "plain"),  
      axis.title.x = element_text(color = "grey20", size = 16, angle = 0, hjust = .5, vjust = 0, face = "plain"),
      axis.title.y = element_text(color = "grey20", size = 16, angle = 90, hjust = .5, vjust = .5, face = "plain"),
      legend.text=element_text(size=14),legend.title=element_text(size=14))

name<- paste0("Result_M5.png")
multi.page <- ggarrange(graph, nrow=1, ncol =1)
ggexport(multi.page, filename = name)