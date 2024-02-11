package us.lsi.tiposrecursivos.ast;

public record ParamDeclaration(String id, Type type) {
	
	public static ParamDeclaration of(String id, Type type) {
		return new ParamDeclaration(id, type);
	}
	
	@Override
	public String toString() {
		return String.format("%s:%s", this.id,this.type);
	}

}
