package components;

import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;

public class TreeNode extends AbstractMutableTreeTableNode{

	
	public TreeNode(Object[] data) {
        super(data);
    }

    @Override
    public Object getValueAt(int column) {
        return getUserObject()[column];
    }

    @Override
    public void setValueAt(Object aValue, int column) {
        getUserObject()[column] = aValue;
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object[] getUserObject() {
        return (Object[]) super.getUserObject();
    }

    @Override
    public boolean isEditable(int column) {
        return true;
    }



}
