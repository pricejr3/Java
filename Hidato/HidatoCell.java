import java.util.LinkedHashSet;

public class HidatoCell {

	private int number;
	LinkedHashSet<Integer> domain = null;

	public HidatoCell(int number) {

		this.number = number;
		this.domain = new LinkedHashSet<Integer>();

	}

	public int getNumber() {
		return this.number;
	}

	public void remove(int i) {
		this.domain.remove(i);

	}

	public LinkedHashSet<Integer> getDomain() {
		return this.domain;

	}

	public void modifyDomain() {

		for (int i = 0; i < Hidato.domainHolder.size(); i++) {
			int value = Hidato.domainHolder.get(i);
			if (this.domain.contains(value)) {
				this.domain.remove(value);
			}
		}

	}
	
	public void directlyAddDomain(int i){
		
		this.domain.add(i);
	}

	public void setDomain(int i) {

		int value = i;

		if (i == 1 && this.number == 0) {

			this.domain.add(i + 1);
		}

		if (i == Hidato.hidatoCellCeiling && this.number == 0) {

			this.domain.add(Hidato.hidatoCellCeiling - 1);
		}

		if (i > 1 && i < Hidato.hidatoCellCeiling && this.number == 0) {

			this.domain.add(i - 1);
			this.domain.add(i + 1);
		}

	}

}
