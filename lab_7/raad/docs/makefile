ASS = timbots
LATEX = pslatex
ADD = 
DST = ..

$(ASS).pdf: $(ASS).tex 
	pdflatex $(ASS).tex

$(ASS).ps: $(ASS).dvi
	dvips -f $(ASS).dvi > $(ASS).ps
	chmod 644 $(ASS).ps

$(ASS).dvi: $(ASS).tex 
	$(LATEX) $(ASS).tex

lp306: $(ASS).ps
	lpr -Php306 $(ASS).ps

lp207: $(ASS).ps
	lpr -Php207 $(ASS).ps

$(ASS).bbl : biblio.bib
	$(LATEX) $(ASS).tex
	bibtex $(ASS)
	$(LATEX) $(ASS).tex
	$(LATEX) $(ASS).tex

open: $(ASS).pdf
	open $(ASS).pdf

copy: $(ASS).pdf
	scp $(ASS).pdf prof4176@bluenose.cs.dal.ca:public_html/$(ASS)/

view: $(ASS).ps
	ghostview $(ASS).ps

html:
	latex2html -no_navigation -info 0 -split 0 $(ASS).tex

spell:
	ispell $(ASS).tex $(ADD)

release: $(ASS).ps
	chmod 644 $(ASS).ps

bib:
	rm -f $(ASS).bbl
	make

force:
	rm -f $(ASS).dvi $(ASS).pdf
	make

tar:
	rm -f $(ASS).tar $(ASS).tar.gz
	tar cvhf $(ASS).tar *
	gzip -9 $(ASS).tar
	mv $(ASS).tar.gz $(DST)
	chmod 644 $(DST)/$(ASS).tar.gz

clean:
	rm -f *.aux *.log *.dvi *.bbl *.blg *.bak $(ASS).ps $(ASS).pdf

todo:
	$(LATEX) todo.tex
	dvips todo.dvi > todo.ps
