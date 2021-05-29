JC = javac
JCFLAGS = -g
J = jar cmvf META-INF/MANIFEST.MF

JFILE = org/mkzh/fincalc/*.java org/mkzh/fincalc/formula/*.java org/mkzh/fincalc/formula/helper/*.java
CLASS = org/mkzh/fincalc/*.class org/mkzh/fincalc/formula/*.class org/mkzh/fincalc/formula/helper/*.class

default: FinancialCalculator.jar

FinancialCalculator.jar:
		$(JC) $(JCFLAGS) $(JFILE)
		$(J) $@ $(CLASS)

clean:
		$(RM) *.jar $(CLASS)